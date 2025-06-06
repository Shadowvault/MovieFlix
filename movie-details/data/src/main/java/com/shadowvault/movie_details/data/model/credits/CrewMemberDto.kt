package com.shadowvault.movie_details.data.model.credits

import com.shadowvault.movie_details.domain.model.credits.CrewPerson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrewMemberDto(
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
    @SerialName("credit_id")
    val creditId: String,
    val department: String,
    val job: String
)

fun CrewMemberDto.toCrewPerson(): CrewPerson {
    return CrewPerson(
        id = id,
        name = name,
        profileImageUrl = profilePath,
        job = job,
        department = department
    )
}