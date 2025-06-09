@file:Suppress("DEPRECATION")

package com.shadowvault.movieflix.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.shadowvault.movieflix.LoginUseCase
import com.shadowvault.movieflix.MainViewModel
import com.shadowvault.movieflix.MovieFlixApp
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<SharedPreferences>(qualifier = named("authPrefs")) {
        EncryptedSharedPreferences(
            androidApplication(),
            "auth_pref",
            MasterKey(androidApplication()),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    single<SharedPreferences>(named("profilePrefs")) {
        androidApplication().getSharedPreferences("profile_pref", Context.MODE_PRIVATE)
    }
    single<CoroutineScope> {
        (androidApplication() as MovieFlixApp).applicationScope
    }

    singleOf(::LoginUseCase)

    viewModelOf(::MainViewModel)
}
