package com.shadowvault.movieflix

import com.shadowvault.auth.domain.account.AccountRepository
import com.shadowvault.auth.domain.account.model.CreateSessionParams
import com.shadowvault.core.domain.profile.ProfileInfo
import com.shadowvault.core.domain.profile.ProfileInfoStorage
import com.shadowvault.core.domain.session.SessionInfo
import com.shadowvault.core.domain.session.SessionStorage
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.Result
import com.shadowvault.core.domain.util.fold

class LoginUseCase(
    private val accountRepository: AccountRepository,
    private val sessionStorage: SessionStorage,
    private val profileInfoStorage: ProfileInfoStorage
) {
    suspend operator fun invoke(requestToken: String): Result<Unit, DataError> {
        return accountRepository.createSession(CreateSessionParams(requestToken))
            .fold(onSuccess = { sessionData ->
                val sessionInfo = SessionInfo(
                    sessionId = sessionData.sessionId,
                )
                sessionStorage.set(sessionInfo)
                accountRepository.getAccountDetails(sessionData.sessionId)
                    .fold(onSuccess = { userDetails ->
                        val profileInfo = ProfileInfo(
                            username = userDetails.username,
                            name = userDetails.name,
                            id = userDetails.id,
                            avatar = userDetails.avatar.tmdb.avatarPath ?: ""
                        )
                        profileInfoStorage.set(profileInfo)
                        Result.Success(Unit)
                    }, onError = { userError ->
                        Result.Error(userError)
                    })
            }, onError = { sessionError ->
                Result.Error(sessionError)
            })
    }
}
