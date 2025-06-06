package com.shadowvault.home.domain.remote

import com.shadowvault.core.domain.movies.MoviesResult
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.Result

interface MoviesRemote {
    suspend fun getPopularMovies(page: Int): Result<MoviesResult, DataError>
}