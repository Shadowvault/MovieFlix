package com.shadowvault.movie_details.data.di

import com.shadowvault.movie_details.data.MovieDetailsRemoteRepositoryImpl
import com.shadowvault.movie_details.domain.MovieDetailsRemoteRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val movieDetailsDataModule = module {
    singleOf(::MovieDetailsRemoteRepositoryImpl).bind<MovieDetailsRemoteRepository>()
}
