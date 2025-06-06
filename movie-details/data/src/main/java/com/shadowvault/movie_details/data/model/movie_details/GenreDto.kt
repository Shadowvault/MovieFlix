package com.shadowvault.movie_details.data.model.movie_details

import kotlinx.serialization.Serializable
import com.shadowvault.movie_details.domain.model.movie_details.Genre

@Serializable
data class GenreDto(
    val id: Int = 0,
    val name: String
)

fun GenreDto.toGenre() = Genre(
    id = id,
    name = name
)