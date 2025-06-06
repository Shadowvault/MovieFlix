package com.shadowvault.movie_details.domain.model.credits

data class CrewPerson(
    val id: Int,
    val name: String,
    val profileImageUrl: String?,
    val job: String,
    val department: String
)