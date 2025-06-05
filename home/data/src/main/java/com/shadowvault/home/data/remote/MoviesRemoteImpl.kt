package com.shadowvault.home.data.remote

import com.shadowvault.core.data.get
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.Result
import com.shadowvault.core.domain.util.map
import com.shadowvault.home.domain.remote.MoviesRemote
import com.shadowvault.home.domain.remote.PopularMoviesResult
import io.ktor.client.HttpClient

class MoviesRemoteImpl(
    private val httpClient: HttpClient
) : MoviesRemote {
    override suspend fun getPopularMovies(page: Int): Result<PopularMoviesResult, DataError> {
        val result = httpClient.get<PopularMoviesResponse>(
            route = "/movie/popular",
            queryParameters = mapOf(
                "language" to "en-US",
                "page" to page,
            ),
        )
        return result.map { it.toPopularMoviesResult()}
    }
}