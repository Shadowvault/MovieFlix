package com.shadowvault.movie_details.data.model.reviews

import com.shadowvault.movie_details.domain.model.reviews.Review
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    val id: String,
    val author: String,
    @SerialName("author_details")
    val authorDetails: AuthorDetailsDto,
    val content: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    val url: String
)

fun ReviewDto.toReview(): Review = Review(
    id = id,
    author = author,
    authorDetails = authorDetails.toAuthorDetails(),
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
    url = url
)
