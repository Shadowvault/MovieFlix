package com.shadowvault.movieflix

import android.app.Application
import com.shadowvault.auth.data.di.authDataModule
import com.shadowvault.auth.presentation.di.authViewModelModule
import com.shadowvault.movieflix.di.appModule
import com.shadowvault.core.data.di.coreDataModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MovieFlixApp: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@MovieFlixApp)
            modules(
                appModule,
                coreDataModule,
                authDataModule,
                authViewModelModule
            )
        }
    }
}