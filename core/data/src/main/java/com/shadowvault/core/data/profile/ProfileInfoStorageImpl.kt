package com.shadowvault.core.data.profile

import android.content.SharedPreferences
import androidx.core.content.edit
import com.shadowvault.core.domain.profile.ProfileInfo
import com.shadowvault.core.domain.profile.ProfileInfoStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class ProfileInfoStorageImpl(private val sharedPreferences: SharedPreferences) : ProfileInfoStorage {

    companion object {
        private const val KEY_PROFILE_INFO = "KEY_PROFILE_INFO"
    }

    override suspend fun get(): ProfileInfo? {
        val json = sharedPreferences.getString(KEY_PROFILE_INFO, null)
        return json?.let {
            Json.decodeFromString<ProfileInfoSerializable>(it).toProfileInfo()
        }
    }

    override suspend fun set(profileInfo: ProfileInfo?) {
        withContext(Dispatchers.IO) {
            if (profileInfo == null) {
                sharedPreferences.edit(commit = true) { remove(KEY_PROFILE_INFO) }
                return@withContext
            }
            val json = Json.encodeToString(profileInfo.toProfileInfoSerializable())
            sharedPreferences.edit {
                putString(KEY_PROFILE_INFO, json)
            }
        }
    }
}
