package com.shadowvault.movie_details.domain.model.reviews

data class ReviewsResult(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val totalPages: Int,
    val totalResults: Int
)