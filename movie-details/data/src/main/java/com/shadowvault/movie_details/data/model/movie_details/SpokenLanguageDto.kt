package com.shadowvault.movie_details.data.model.movie_details

import com.shadowvault.movie_details.domain.model.movie_details.SpokenLanguage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguageDto(
    @SerialName("english_name")
    val englishName: String,
    @SerialName("iso_639_1")
    val iso6391: String,
    val name: String
)

fun SpokenLanguageDto.toSpokenLanguage() = SpokenLanguage(
    englishName = englishName,
    iso6391 = iso6391,
    name = name
)
