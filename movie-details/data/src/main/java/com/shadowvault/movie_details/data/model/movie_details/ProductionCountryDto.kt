package com.shadowvault.movie_details.data.model.movie_details

import com.shadowvault.movie_details.domain.model.movie_details.ProductionCountry
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountryDto(
    @SerialName("iso_3166_1") val iso31661: String,
    val name: String
)

fun ProductionCountryDto.toProductionCountry() = ProductionCountry(
    iso31661 = iso31661,
    name = name
)