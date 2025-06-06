package com.shadowvault.home.data.remote

import com.shadowvault.core.data.get
import com.shadowvault.core.data.movies.MoviesResponse
import com.shadowvault.core.data.movies.toMoviesResult
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.Result
import com.shadowvault.core.domain.util.map
import com.shadowvault.home.domain.remote.MoviesRemote
import com.shadowvault.core.domain.movies.MoviesResult
import io.ktor.client.HttpClient

class MoviesRemoteImpl(
    private val httpClient: HttpClient
) : MoviesRemote {
    override suspend fun getPopularMovies(page: Int): Result<MoviesResult, DataError> {
        val result = httpClient.get<MoviesResponse>(
            route = "/movie/popular",
            queryParameters = mapOf(
                "language" to "en-US",
                "page" to page,
            ),
        )
        return result.map { it.toMoviesResult()}
    }
}