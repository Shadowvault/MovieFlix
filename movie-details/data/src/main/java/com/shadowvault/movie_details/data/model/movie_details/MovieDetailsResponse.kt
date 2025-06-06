package com.shadowvault.movie_details.data.model.movie_details

import com.shadowvault.core.data.util.FlexibleValueDto
import com.shadowvault.core.data.util.toFlexibleValue
import com.shadowvault.movie_details.domain.model.movie_details.MovieDetailsResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    val adult: Boolean = true,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("belongs_to_collection")
    @Serializable(with = CollectionValueSerializer::class)
    val belongsToCollection: FlexibleValueDto<CollectionDto>? = null, // Sometimes this can be an object, check API spec
    val budget: Int = 0,
    val genres: List<GenreDto> = emptyList(),
    val homepage: String? = null,
    val id: Int = 0,
    @SerialName("imdb_id")
    val imdbId: String? = null,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String? = null,
    val popularity: Double = 0.0,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyDto> = emptyList(),
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountryDto> = emptyList(),
    @SerialName("release_date")
    val releaseDate: String,
    val revenue: Int = 0,
    val runtime: Int = 0,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDto> = emptyList(),
    val status: String,
    val tagline: String? = null,
    val title: String,
    val video: Boolean = true,
    @SerialName("vote_average") val voteAverage: Double = 0.0,
    @SerialName("vote_count") val voteCount: Int = 0
)

fun MovieDetailsResponse.toMovieDetailsResult() = MovieDetailsResult(
    adult = adult,
    backdropPath = "https://image.tmdb.org/t/p/w780${backdropPath}",
    belongsToCollection = belongsToCollection?.toFlexibleValue { it.toCollection() },
    budget = budget,
    genres = genres.map { it.toGenre() },
    homepage = homepage,
    id = id,
    imdbId = imdbId,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = "https://image.tmdb.org/t/p/w500${posterPath}",
    productionCompanies = productionCompanies.map { it.toProductionCompany() },
    productionCountries = productionCountries.map { it.toProductionCountry() },
    releaseDate = releaseDate,
    revenue = revenue,
    runtime = runtime,
    spokenLanguages = spokenLanguages.map { it.toSpokenLanguage() },
    status = status,
    tagline = tagline,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)