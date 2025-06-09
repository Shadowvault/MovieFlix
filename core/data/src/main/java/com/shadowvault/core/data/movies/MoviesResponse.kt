package com.shadowvault.core.data.movies

import com.shadowvault.core.domain.movies.MoviesResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    val page: Int,
    val results: List<MovieDto>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

fun MoviesResponse.toMoviesResult(likedIds: Set<Int> = emptySet()): MoviesResult {
    return MoviesResult(
        page = page,
        totalPages = totalPages,
        totalResults = totalResults,
        movies = results.map { it.toMovie(isLiked = likedIds.contains(it.id)) }
    )
}
