package com.shadowvault.auth.presentation.di

import com.shadowvault.auth.presentation.login.LoginScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authViewModelModule = module {
    viewModelOf(::LoginScreenViewModel)
}
