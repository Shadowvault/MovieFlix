package com.shadowvault.core.data.session

import android.content.SharedPreferences
import androidx.core.content.edit
import com.shadowvault.core.domain.session.SessionInfo
import com.shadowvault.core.domain.session.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class EncryptedSessionStorage(
    private val sharedPreferences: SharedPreferences
) : SessionStorage {

    companion object {
        private const val KEY_AUTH_INFO = "KEY_AUTH_INFO"
    }

    override suspend fun get(): SessionInfo? {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_AUTH_INFO, null)
            json?.let {
                Json.decodeFromString<SessionInfoSerializable>(it).SessionInfo()
            }
        }
    }

    override suspend fun set(info: SessionInfo?) {
        withContext(Dispatchers.IO) {
            if (info == null) {
                sharedPreferences.edit(commit = true) { remove(KEY_AUTH_INFO) }
                return@withContext
            }

            val json = Json.encodeToString(info.toSessionInfoSerializable())
            sharedPreferences.edit(commit = true) {
                putString(KEY_AUTH_INFO, json)
            }
        }
    }
}
