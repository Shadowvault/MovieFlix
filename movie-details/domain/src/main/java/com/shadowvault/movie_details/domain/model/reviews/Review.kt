package com.shadowvault.movie_details.domain.model.reviews

data class Review(
    val id: String,
    val author: String,
    val authorDetails: AuthorDetails,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val url: String
)
