package com.shadowvault.auth.domain.account

import com.shadowvault.auth.domain.account.model.AccountDetailsResult
import com.shadowvault.auth.domain.account.model.CreateRequestTokenResult
import com.shadowvault.auth.domain.account.model.CreateSessionParams
import com.shadowvault.auth.domain.account.model.CreateSessionResult
import com.shadowvault.auth.domain.account.model.DeleteSessionResult
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.Result

interface AccountRepository {
    suspend fun createRequestToken(): Result<CreateRequestTokenResult, DataError>
    suspend fun createSession(requestToken: CreateSessionParams): Result<CreateSessionResult, DataError>
    suspend fun deleteSession(sessionId: String): Result<DeleteSessionResult, DataError>
    suspend fun getAccountDetails(sessionId: String): Result<AccountDetailsResult, DataError>
}