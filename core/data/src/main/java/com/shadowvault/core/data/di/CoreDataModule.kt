package com.shadowvault.core.data.di

import com.shadowvault.core.data.HttpClientFactory
import com.shadowvault.core.data.profile.ProfileInfoStorageImpl
import com.shadowvault.core.data.session.EncryptedSessionStorage
import com.shadowvault.core.domain.profile.ProfileInfoStorage
import com.shadowvault.core.domain.session.SessionStorage
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coreDataModule = module {

    single {
        HttpClientFactory().build()
    }
    single<SessionStorage> {
        EncryptedSessionStorage(get(named("authPrefs")))
    }
    single<ProfileInfoStorage> {
        ProfileInfoStorageImpl(get(named("profilePrefs")))
    }
}