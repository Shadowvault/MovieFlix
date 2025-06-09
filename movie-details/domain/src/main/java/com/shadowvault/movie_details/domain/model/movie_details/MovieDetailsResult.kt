package com.shadowvault.movie_details.domain.model.movie_details

import com.shadowvault.core.domain.util.FlexibleValue

data class MovieDetailsResult(
    val adult: Boolean,
    val backdropPath: String?,
    val belongsToCollection: FlexibleValue<Collection>?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val imdbId: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
) {
    companion object {
        fun empty() = MovieDetailsResult(
            adult = false,
            backdropPath = null,
            belongsToCollection = null,
            budget = 0,
            genres = emptyList(),
            homepage = null,
            id = -1,
            imdbId = null,
            originalLanguage = "",
            originalTitle = "",
            overview = "",
            popularity = 0.0,
            posterPath = null,
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            releaseDate = "",
            revenue = 0,
            runtime = 0,
            spokenLanguages = emptyList(),
            status = "",
            tagline = "",
            title = "",
            video = false,
            voteAverage = 0.0,
            voteCount = 0
        )
    }
}
