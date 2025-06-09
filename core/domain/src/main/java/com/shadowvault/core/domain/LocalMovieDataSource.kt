package com.shadowvault.core.domain

import androidx.paging.PagingSource
import com.shadowvault.core.domain.movies.Movie
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface LocalMovieDataSource {
    fun getPagedMovies(): PagingSource<Int, Movie>
    suspend fun insertAllMovies(movies: List<Movie>, page: Int): Result<List<Int>, DataError.Local>
    fun getLikedMovieIdsFlow(userId: Int): Flow<List<Int>>
    fun isMovieLikedFlow(userId: Int, movieId: Int): Flow<Boolean>
    suspend fun likeMovie(userId: Int, movieId: Int)
    suspend fun unlikeMovie(userId: Int, movieId: Int)
    suspend fun clearAllMovies()
}
