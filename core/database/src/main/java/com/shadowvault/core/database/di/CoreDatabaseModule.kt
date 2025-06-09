package com.shadowvault.core.database.di

import androidx.room.Room
import com.shadowvault.core.database.MovieFlixDatabase
import com.shadowvault.core.database.RoomMovieDataSource
import com.shadowvault.core.domain.LocalMovieDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            MovieFlixDatabase::class.java,
            "movieflix.db"
        ).build()
    }

    single { get<MovieFlixDatabase>().movieDao }
    single { get<MovieFlixDatabase>().remoteKeysDao }
    singleOf(::RoomMovieDataSource).bind<LocalMovieDataSource>()
}
