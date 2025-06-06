package com.shadowvault.movie_details.domain

import com.shadowvault.core.domain.movies.MoviesResult
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.Result
import com.shadowvault.movie_details.domain.model.credits.CreditsResult
import com.shadowvault.movie_details.domain.model.movie_details.MovieDetailsResult
import com.shadowvault.movie_details.domain.model.reviews.ReviewsResult

interface MovieDetailsRemoteRepository {
    suspend fun getDetails(movieId: Int): Result<MovieDetailsResult, DataError>
    suspend fun getCredits(movieId: Int): Result<CreditsResult, DataError>
    suspend fun getReviews(movieId: Int, page: Int): Result<ReviewsResult, DataError>
    suspend fun getSimilarMovies(movieId: Int, page: Int): Result<MoviesResult, DataError>
}
