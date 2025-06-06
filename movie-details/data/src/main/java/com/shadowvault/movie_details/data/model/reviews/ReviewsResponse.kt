package com.shadowvault.movie_details.data.model.reviews

import com.shadowvault.movie_details.domain.model.reviews.ReviewsResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewsResponse(
    val id: Int = 0,
    val page: Int = 0,
    val results: List<ReviewDto> = emptyList(),
    @SerialName("total_pages")
    val totalPages: Int = 0,
    @SerialName("total_results")
    val totalResults: Int = 0
)

fun ReviewsResponse.toReviewsResult(): ReviewsResult = ReviewsResult(
    id = id,
    page = page,
    results = results.map { it.toReview() },
    totalPages = totalPages,
    totalResults = totalResults
)
