package com.shadowvault.core.database

import androidx.paging.PagingSource
import com.shadowvault.core.domain.LocalMovieDataSource
import com.shadowvault.core.domain.movies.Movie
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

class RoomMovieDataSource(
    private val movieDao: MovieDao
) : LocalMovieDataSource {

    override fun getPagedMovies(): PagingSource<Int, Movie> {
        val source: PagingSource<Int, MovieEntity> = movieDao.getPagedMovies()
        return MappedPagingSource(source) { it.toMovie() }
    }

    @Suppress("TooGenericExceptionCaught")
    override suspend fun insertAllMovies(
        movies: List<Movie>,
        page: Int
    ): Result<List<Int>, DataError.Local> {
        val currentTime = System.currentTimeMillis()
        return try {
            val entities = movies.map { it.toMovieEntity(page = page, currentTime) }
            movieDao.insertAll(entities)
            Result.Success(entities.map { it.id })
        } catch (e: Exception) {
            Result.Error(e.toLocalError())
        }
    }

    override fun getLikedMovieIdsFlow(userId: Int): Flow<List<Int>> {
        return movieDao.getLikedMovieIdsFlow(userId)
    }

    override fun isMovieLikedFlow(userId: Int, movieId: Int): Flow<Boolean> {
        return movieDao.isMovieLikedFlow(userId, movieId)
    }

    override suspend fun likeMovie(userId: Int, movieId: Int) {
        movieDao.likeMovie(LikedMovieEntity(userId, movieId))
    }

    override suspend fun unlikeMovie(userId: Int, movieId: Int) {
        movieDao.unlikeMovie(userId, movieId)
    }

    override suspend fun clearAllMovies() {
        movieDao.clearAllMovies()
    }

    private fun Exception.toLocalError(): DataError.Local = when (this) {
        is SecurityException -> DataError.Local.PERMISSION_DENIED
        is java.io.IOException -> DataError.Local.DISK_FULL
        else -> DataError.Local.CACHE_FAILURE
    }
}
