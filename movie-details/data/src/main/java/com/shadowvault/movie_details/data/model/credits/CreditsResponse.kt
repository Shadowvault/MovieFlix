package com.shadowvault.movie_details.data.model.credits

import com.shadowvault.movie_details.domain.model.credits.CreditsResult
import kotlinx.serialization.Serializable

@Serializable
data class CreditsResponse(
    val id: Int = 0,
    val cast: List<CastMemberDto> = emptyList(),
    val crew: List<CrewMemberDto> = emptyList()
)

fun CreditsResponse.toCreditsResult(): CreditsResult {
    return CreditsResult(
        movieId = id,
        cast = cast.map { it.toCastPerson() },
        crew = crew.map { it.toCrewPerson() }
    )
}
