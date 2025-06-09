package com.shadowvault.movie_details.data.model.reviews

import com.shadowvault.movie_details.domain.model.reviews.AuthorDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorDetailsDto(
    val name: String? = null,
    val username: String? = null,
    @SerialName("avatar_path")
    val avatarPath: String? = null,
    val rating: Double? = null
)

fun AuthorDetailsDto.toAuthorDetails(): AuthorDetails = AuthorDetails(
    name, username, avatarPath, rating
)
