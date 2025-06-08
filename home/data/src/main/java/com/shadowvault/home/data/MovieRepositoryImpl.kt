package com.shadowvault.home.data

import MovieRemoteMediator
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.shadowvault.core.database.MovieDao
import com.shadowvault.core.database.MovieFlixDatabase
import com.shadowvault.core.database.keys.RemoteKeysDao
import com.shadowvault.core.database.toMovie
import com.shadowvault.core.domain.LocalMovieDataSource
import com.shadowvault.core.domain.movies.Movie
import com.shadowvault.home.domain.MovieRepository
import com.shadowvault.home.domain.remote.RemoteMovieDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val database: MovieFlixDatabase,
    private val moviesLocal: LocalMovieDataSource,
    private val moviesRemote: RemoteMovieDataSource,
    private val movieDao: MovieDao,
    private val keysDao: RemoteKeysDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedMovies(userId: Int, scope: CoroutineScope): Flow<PagingData<Movie>> {
        val pagerFlow = Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1,
                initialLoadSize = 20,
            ),
            pagingSourceFactory = { movieDao.getPagedMovies() },
            remoteMediator = MovieRemoteMediator(moviesRemote, database)
        ).flow.cachedIn(scope)

        val likedMovieIdsFlow = movieDao.getLikedMovieIdsFlow(userId)

        return pagerFlow.combine(likedMovieIdsFlow) { pagingData, likedIds ->
            pagingData.map { movieEntity ->
                movieEntity.toMovie().copy(isLiked = likedIds.contains(movieEntity.id))
            }
        }
    }

    override suspend fun toggleLike(userId: Int, movieId: Int, like: Boolean) =
        withContext(ioDispatcher) {
            if (like) {
                moviesLocal.likeMovie(userId, movieId)
            } else {
                moviesLocal.unlikeMovie(userId, movieId)
            }
        }

    override suspend fun clear() {
        movieDao.clearAllMovies()
        keysDao.clearRemoteKeys()
    }
}
