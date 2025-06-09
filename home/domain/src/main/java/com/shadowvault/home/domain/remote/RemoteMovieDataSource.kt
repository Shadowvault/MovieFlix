package com.shadowvault.home.domain.remote

import com.shadowvault.core.domain.movies.MoviesResult
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.Result

interface RemoteMovieDataSource {
    suspend fun getPopularMovies(page: Int): Result<MoviesResult, DataError>
}
