package com.shadowvault.auth.data.account

import com.shadowvault.auth.data.account.model.AccountDetailsResponse
import com.shadowvault.auth.data.account.model.CreateRequestTokenResponse
import com.shadowvault.auth.data.account.model.CreateSessionRequest
import com.shadowvault.auth.data.account.model.CreateSessionResponse
import com.shadowvault.auth.data.account.model.toAccountDetailsResult
import com.shadowvault.auth.data.account.model.toCreateRequestTokenResult
import com.shadowvault.auth.data.account.model.toCreateSessionRequest
import com.shadowvault.auth.data.account.model.toCreateSessionResult
import com.shadowvault.auth.domain.account.AccountRepository
import com.shadowvault.auth.domain.account.model.AccountDetailsResult
import com.shadowvault.auth.domain.account.model.CreateRequestTokenResult
import com.shadowvault.auth.domain.account.model.CreateSessionParams
import com.shadowvault.auth.domain.account.model.CreateSessionResult
import com.shadowvault.auth.domain.account.model.DeleteSessionResult
import com.shadowvault.core.data.get
import com.shadowvault.core.data.post
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.map
import com.shadowvault.core.domain.util.Result
import io.ktor.client.HttpClient


class AccountRepositoryImpl(
    private val httpClient: HttpClient
) : AccountRepository {
    override suspend fun createRequestToken(): Result<CreateRequestTokenResult, DataError> {
        val result = httpClient.get<CreateRequestTokenResponse>(
            route = "/authentication/token/new",
        )
        return result.map { it.toCreateRequestTokenResult() }
    }

    override suspend fun createSession(requestToken: CreateSessionParams): Result<CreateSessionResult, DataError> {

        val result = httpClient.post<CreateSessionRequest, CreateSessionResponse>(
            route = "/authentication/session/new",
            body = requestToken.toCreateSessionRequest()
        )
        return result.map { it.toCreateSessionResult()}
    }

    override suspend fun deleteSession(sessionId: String): Result<DeleteSessionResult, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountDetails(sessionId: String): Result<AccountDetailsResult, DataError> {
        val result = httpClient.get<AccountDetailsResponse>(
            route = "/account",
            queryParameters = mapOf("session_id" to sessionId),
        )
        return result.map { it.toAccountDetailsResult() }
    }

}