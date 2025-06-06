package com.shadowvault.movie_details.data.model.credits

import com.shadowvault.movie_details.domain.model.credits.CastPerson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastMemberDto(
    val adult: Boolean = true,
    val gender: Int = 0,
    val id: Int = 0,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double = 0.0,
    @SerialName("profile_path")
    val profilePath: String? = null,
    @SerialName("cast_id")
    val castId: Int = 0,
    val character: String,
    @SerialName("credit_id") val creditId: String,
    val order: Int = 0
)

fun CastMemberDto.toCastPerson(): CastPerson {
    return CastPerson(
        id = id,
        name = name,
        profileImageUrl = profilePath,
        character = character
    )
}