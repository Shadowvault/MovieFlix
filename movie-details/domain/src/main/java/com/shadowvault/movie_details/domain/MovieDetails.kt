package com.shadowvault.movie_details.domain

import com.shadowvault.core.domain.movies.Movie
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.movie_details.domain.model.credits.CastPerson
import com.shadowvault.movie_details.domain.model.movie_details.MovieDetailsResult
import com.shadowvault.movie_details.domain.model.reviews.Review

data class MovieDetails(
    val details: MovieDetailsResult,
    val isFavorite: Boolean,
    val cast: List<CastPerson>,
    val reviews: List<Review>,
    val similarMovies: List<Movie>,
    val errors: List<DataError>
)
