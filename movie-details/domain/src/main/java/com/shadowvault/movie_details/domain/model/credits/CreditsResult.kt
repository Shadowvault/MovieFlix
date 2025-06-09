package com.shadowvault.movie_details.domain.model.credits

data class CreditsResult(
    val movieId: Int,
    val cast: List<CastPerson>,
    val crew: List<CrewPerson>
)
