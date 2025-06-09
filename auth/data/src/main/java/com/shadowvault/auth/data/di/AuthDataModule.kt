package com.shadowvault.auth.data.di

import com.shadowvault.auth.data.account.AccountRepositoryImpl
import com.shadowvault.auth.domain.account.AccountRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    singleOf(::AccountRepositoryImpl).bind<AccountRepository>()
}
