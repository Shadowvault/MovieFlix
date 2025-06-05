package com.shadowvault.home.data.remote

import com.shadowvault.home.domain.remote.PopularMoviesResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularMoviesResponse(
    val page: Int,
    val results: List<MovieDto>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

fun PopularMoviesResponse.toPopularMoviesResult(likedIds: Set<Int> = emptySet()): PopularMoviesResult {
    return PopularMoviesResult(
        page = page,
        totalPages = totalPages,
        totalResults = totalResults,
        movies = results.map { it.toMovie(isLiked = likedIds.contains(it.id)) }
    )
}

