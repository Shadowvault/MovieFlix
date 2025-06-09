package com.shadowvault.home.presentation.di

import com.shadowvault.home.presentation.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homeViewModelModule = module {
    viewModelOf(::HomeScreenViewModel)
}
