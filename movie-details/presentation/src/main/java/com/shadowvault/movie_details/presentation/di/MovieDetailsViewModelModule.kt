package com.shadowvault.movie_details.presentation.di

import com.shadowvault.movie_details.domain.GetMovieDetailsUseCase
import com.shadowvault.movie_details.presentation.MovieDetailsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val movieDetailsViewModelModule = module {
    factory { GetMovieDetailsUseCase(get()) }
    viewModelOf(::MovieDetailsScreenViewModel)

}