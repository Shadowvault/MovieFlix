import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shadowvault.core.database.MovieEntity
import com.shadowvault.core.database.MovieFlixDatabase
import com.shadowvault.core.database.keys.RemoteKeysEntity
import com.shadowvault.core.database.toMovieEntity
import com.shadowvault.core.domain.movies.Movie
import com.shadowvault.core.domain.util.fold
import com.shadowvault.core.domain.util.toThrowable
import com.shadowvault.home.domain.remote.RemoteMovieDataSource
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val remoteMovieDataSource: RemoteMovieDataSource,
    private val movieDatabase: MovieFlixDatabase
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (movieDatabase.remoteKeysDao.getCreationTime()
                ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            movieDatabase.remoteKeysDao.remoteKeysByMovieId(movie.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            movieDatabase.remoteKeysDao.remoteKeysByMovieId(movie.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                movieDatabase.remoteKeysDao.remoteKeysByMovieId(id)
            }
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }


        remoteMovieDataSource.getPopularMovies(page = page).fold(
            onSuccess = { data ->
                val movies = data.movies
                val endOfPaginationReached = movies.isEmpty()

                movieDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        movieDatabase.remoteKeysDao.clearRemoteKeys()
                        movieDatabase.movieDao.clearAllMovies()
                    }
                    val prevKey = if (page > 1) page - 1 else null
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val remoteKeys = movies.map {
                        RemoteKeysEntity(
                            movieID = it.id,
                            prevKey = prevKey,
                            currentPage = page,
                            nextKey = nextKey
                        )
                    }
                    val currentTime = System.currentTimeMillis()
                    movieDatabase.remoteKeysDao.insertAll(remoteKeys)
                    movieDatabase.movieDao.insertAll(movies.mapIndexed { _, movie ->
                        movie.toMovieEntity(page = page, currentTime = currentTime)
                    })
                }
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            },
            onError = { error ->
                return MediatorResult.Error(error.toThrowable())
            }
        )
    }
}
