package com.shadowvault.movie_details.domain

import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.fold
import com.shadowvault.movie_details.domain.model.movie_details.MovieDetailsResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetMovieDetailsUseCase(
    private val remoteRepository: MovieDetailsRemoteRepository
) {
    operator fun invoke(movieId: Int): Flow<MovieDetails> = flow {
        val errors = mutableListOf<DataError>()

        var currentState = MovieDetails(
            details = MovieDetailsResult.empty(),
            isFavorite = false,
            cast = emptyList(),
            reviews = emptyList(),
            similarMovies = emptyList(),
            errors = emptyList()
        )

        remoteRepository.getDetails(movieId).fold(
            onSuccess = { details ->
                currentState = currentState.copy(details = details)
                emit(currentState)
            },
            onError = { error -> errors.add(error) }
        )

        remoteRepository.getCredits(movieId).fold(
            onSuccess = { credits ->
                currentState = currentState.copy(cast = credits.cast)
                emit(currentState)
            },
            onError = { error -> errors.add(error) }
        )

        remoteRepository.getReviews(movieId, 1).fold(
            onSuccess = { reviews ->
                currentState = currentState.copy(reviews = reviews.results.take(REVIEWS_LIMIT))
                emit(currentState)
            },
            onError = { error -> errors.add(error) }
        )

        remoteRepository.getSimilarMovies(movieId, 1).fold(
            onSuccess = { similar ->
                currentState = currentState.copy(similarMovies = similar.movies.take(SIMILAR_MOVIES_LIMIT))
                emit(currentState)
            },
            onError = { error -> errors.add(error) }
        )

        if (errors.isNotEmpty()) {
            emit(currentState)
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        private const val REVIEWS_LIMIT = 3
        private const val SIMILAR_MOVIES_LIMIT = 6
    }
}
