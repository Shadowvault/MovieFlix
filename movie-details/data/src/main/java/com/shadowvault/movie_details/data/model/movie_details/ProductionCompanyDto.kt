package com.shadowvault.movie_details.data.model.movie_details

import com.shadowvault.movie_details.domain.model.movie_details.ProductionCompany
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompanyDto(
    val id: Int = 0,
    @SerialName("logo_path")
    val logoPath: String? = null,
    val name: String,
    @SerialName("origin_country")
    val originCountry: String
)

fun ProductionCompanyDto.toProductionCompany() = ProductionCompany(
    id = id,
    logoPath = logoPath,
    name = name,
    originCountry = originCountry
)