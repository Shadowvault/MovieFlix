package com.shadowvault.movieflix

import android.app.Application
import com.shadowvault.auth.data.di.authDataModule
import com.shadowvault.auth.presentation.di.authViewModelModule
import com.shadowvault.core.data.di.coreDataModule
import com.shadowvault.core.database.di.coreDatabaseModule
import com.shadowvault.home.data.di.homeDataModule
import com.shadowvault.home.presentation.di.homeViewModelModule
import com.shadowvault.movie_details.data.di.movieDetailsDataModule
import com.shadowvault.movie_details.presentation.di.movieDetailsViewModelModule
import com.shadowvault.movieflix.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MovieFlixApp : Application() {

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
                coreDatabaseModule,
                authDataModule,
                authViewModelModule,
                homeDataModule,
                homeViewModelModule,
                movieDetailsDataModule,
                movieDetailsViewModelModule
            )
        }
    }
}
