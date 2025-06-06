package com.shadowvault.core.data.movies

import com.shadowvault.core.domain.movies.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("genre_ids") val genreIds: List<Int>,
    val id: Int,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)

fun MovieDto.toMovie(isLiked: Boolean = false): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = "https://image.tmdb.org/t/p/w500${posterPath}",
        backdropPath = "https://image.tmdb.org/t/p/w780${backdropPath}",
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        isAdult = adult,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        popularity = popularity,
        genreIds = genreIds,
        isVideo = video,
        isLiked = isLiked
    )
}