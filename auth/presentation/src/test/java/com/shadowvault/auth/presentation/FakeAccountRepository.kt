import com.shadowvault.auth.domain.account.AccountRepository
import com.shadowvault.auth.domain.account.model.AccountDetailsResult
import com.shadowvault.auth.domain.account.model.CreateRequestTokenResult
import com.shadowvault.auth.domain.account.model.CreateSessionParams
import com.shadowvault.auth.domain.account.model.CreateSessionResult
import com.shadowvault.auth.domain.account.model.DeleteSessionResult
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.Result

class FakeAccountRepository : AccountRepository {
    var tokenResult: Result<CreateRequestTokenResult, DataError> =
        Result.Success(
            CreateRequestTokenResult(
                success = true,
                expiresAt = "2025-12-31",
                requestToken = "fake_token"
            )
        )

    override suspend fun createRequestToken(): Result<CreateRequestTokenResult, DataError> {
        return tokenResult
    }

    override suspend fun createSession(requestToken: CreateSessionParams): Result<CreateSessionResult, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSession(sessionId: String): Result<DeleteSessionResult, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountDetails(sessionId: String): Result<AccountDetailsResult, DataError> {
        TODO("Not yet implemented")
    }
}
