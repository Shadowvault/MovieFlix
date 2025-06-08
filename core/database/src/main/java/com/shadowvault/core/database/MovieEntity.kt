package com.shadowvault.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shadowvault.core.domain.movies.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val isAdult: Boolean,
    val originalLanguage: String,
    val originalTitle: String,
    val popularity: Double,
    val genreIds: List<Int>,
    val isVideo: Boolean,
    @ColumnInfo(name = "page")
    val page: Int,
    val currentTime: Long
)

fun MovieEntity.toMovie(isLiked: Boolean = false): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    isAdult = isAdult,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    popularity = popularity,
    genreIds = genreIds,
    isVideo = isVideo,
    isLiked = isLiked
)

fun Movie.toMovieEntity(page: Int, currentTime: Long): MovieEntity = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    isAdult = isAdult,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    popularity = popularity,
    genreIds = genreIds,
    isVideo = isVideo,
    page = page,
    currentTime = currentTime
)

