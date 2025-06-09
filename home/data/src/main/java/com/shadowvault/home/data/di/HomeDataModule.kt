package com.shadowvault.home.data.di

import com.shadowvault.home.data.MovieRepositoryImpl
import com.shadowvault.home.data.remote.RemoteMovieDataSourceImpl
import com.shadowvault.home.domain.MovieRepository
import com.shadowvault.home.domain.remote.RemoteMovieDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeDataModule = module {
    singleOf(::RemoteMovieDataSourceImpl).bind<RemoteMovieDataSource>()
    single<MovieRepository> {
        MovieRepositoryImpl(
            database = get(),
            moviesLocal = get(),
            moviesRemote = get(),
            movieDao = get(),
            keysDao = get()
        )
    }
}
