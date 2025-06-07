package com.shadowvault.core.database

import androidx.room.Entity

@Entity(tableName = "liked_movies", primaryKeys = ["userId", "movieId"])
data class LikedMovieEntity(
    val userId: Int,
    val movieId: Int
)