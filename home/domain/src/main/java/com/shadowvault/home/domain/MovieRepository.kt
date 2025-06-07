package com.shadowvault.home.domain

import androidx.paging.PagingData
import com.shadowvault.core.domain.movies.Movie
import com.shadowvault.core.domain.movies.MoviesResult
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPagedMovies(userId: Int, scope: CoroutineScope): Flow<PagingData<Movie>>
    suspend fun toggleLike(userId: Int, movieId: Int, like: Boolean)
}