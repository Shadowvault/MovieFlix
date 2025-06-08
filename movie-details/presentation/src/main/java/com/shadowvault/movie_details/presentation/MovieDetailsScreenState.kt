package com.shadowvault.movie_details.presentation

import com.shadowvault.core.domain.movies.Movie
import com.shadowvault.movie_details.domain.model.credits.CastPerson
import com.shadowvault.movie_details.domain.model.reviews.Review

data class MovieDetailsScreenState(
    val isLoading: Boolean = false,
    val movieId: Int = 0,
    val title: String = "",
    val backdropPath: String? = null,
    val posterPath: String? = null,
    val genres: List<String> = emptyList(),
    val releaseDate: String = "",
    val runtimeMinutes: Int? = null,
    val starRating: Float = 0f,
    val isFavorite: Boolean = false,
    val description: String = "",
    val cast: List<CastPerson> = emptyList(),
    val reviews: List<Review> = emptyList(),
    val similarMovies: List<Movie> = emptyList()
)
