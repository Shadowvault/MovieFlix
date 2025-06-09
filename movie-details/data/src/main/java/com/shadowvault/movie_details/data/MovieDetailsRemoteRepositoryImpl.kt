package com.shadowvault.movie_details.data

import com.shadowvault.core.data.get
import com.shadowvault.core.data.movies.MoviesResponse
import com.shadowvault.core.data.movies.toMoviesResult
import com.shadowvault.core.domain.movies.MoviesResult
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.Result
import com.shadowvault.core.domain.util.map
import com.shadowvault.movie_details.data.model.credits.CreditsResponse
import com.shadowvault.movie_details.data.model.credits.toCreditsResult
import com.shadowvault.movie_details.data.model.movie_details.MovieDetailsResponse
import com.shadowvault.movie_details.data.model.movie_details.toMovieDetailsResult
import com.shadowvault.movie_details.data.model.reviews.ReviewsResponse
import com.shadowvault.movie_details.data.model.reviews.toReviewsResult
import com.shadowvault.movie_details.domain.MovieDetailsRemoteRepository
import com.shadowvault.movie_details.domain.model.credits.CreditsResult
import com.shadowvault.movie_details.domain.model.movie_details.MovieDetailsResult
import com.shadowvault.movie_details.domain.model.reviews.ReviewsResult
import io.ktor.client.HttpClient

class MovieDetailsRemoteRepositoryImpl(
    private val httpClient: HttpClient
) : MovieDetailsRemoteRepository {

    override suspend fun getDetails(movieId: Int): Result<MovieDetailsResult, DataError> {
        val result = httpClient.get<MovieDetailsResponse>(
            route = "/movie/$movieId",
            queryParameters = mapOf(
                "language" to "en-US"
            )
        )
        return result.map { it.toMovieDetailsResult() }
    }

    override suspend fun getCredits(movieId: Int): Result<CreditsResult, DataError> {
        val result = httpClient.get<CreditsResponse>(
            route = "/movie/$movieId/credits",
            queryParameters = mapOf("language" to "en-US")
        )
        return result.map { it.toCreditsResult() }
    }

    override suspend fun getReviews(movieId: Int, page: Int): Result<ReviewsResult, DataError> {
        val result = httpClient.get<ReviewsResponse>(
            route = "/movie/$movieId/reviews",
            queryParameters = mapOf("language" to "en-US", "page" to page)
        )
        return result.map { it.toReviewsResult() }
    }

    override suspend fun getSimilarMovies(
        movieId: Int,
        page: Int
    ): Result<MoviesResult, DataError> {
        val result = httpClient.get<MoviesResponse>(
            route = "/movie/$movieId/similar",
            queryParameters = mapOf("language" to "en-US", "page" to page)
        )
        return result.map { it.toMoviesResult() }
    }
}
