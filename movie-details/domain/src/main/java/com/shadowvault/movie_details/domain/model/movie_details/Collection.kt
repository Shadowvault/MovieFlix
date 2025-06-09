package com.shadowvault.movie_details.domain.model.movie_details

data class Collection(
    val id: Int,
    val name: String,
    val posterPath: String?,
    val backdropPath: String?
)
