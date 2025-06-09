package com.shadowvault.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shadowvault.core.database.keys.RemoteKeysDao
import com.shadowvault.core.database.keys.RemoteKeysEntity

@Database(
    entities = [
        MovieEntity::class,
        LikedMovieEntity::class,
        RemoteKeysEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MovieFlixDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val remoteKeysDao: RemoteKeysDao
}
