package com.shadowvault.home.data.di

import com.shadowvault.home.data.remote.MoviesRemoteImpl
import com.shadowvault.home.domain.remote.MoviesRemote
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeDataModule = module {
    singleOf(::MoviesRemoteImpl).bind<MoviesRemote>()
}