package com.shadowvault.core.database

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromGenreIds(genreIds: List<Int>): String = genreIds.joinToString(",")

    @TypeConverter
    fun toGenreIds(data: String): List<Int> =
        if (data.isBlank()) emptyList() else data.split(",").mapNotNull { it.toIntOrNull() }
}
