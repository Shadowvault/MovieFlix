package com.shadowvault.movie_details.domain.model.credits

data class CastPerson(
    val id: Int,
    val name: String,
    val profileImageUrl: String?,
    val character: String
)
