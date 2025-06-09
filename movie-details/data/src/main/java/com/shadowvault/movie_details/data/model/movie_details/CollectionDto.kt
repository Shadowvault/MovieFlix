package com.shadowvault.movie_details.data.model.movie_details

import com.shadowvault.movie_details.domain.model.movie_details.Collection
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionDto(
    val id: Int,
    val name: String,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null
)

fun CollectionDto.toCollection(): Collection {
    return Collection(id, name, posterPath, backdropPath)
}
