package com.shadowvault.auth.presentation

import FakeAccountRepository
import com.shadowvault.auth.domain.account.model.CreateRequestTokenResult
import com.shadowvault.auth.presentation.login.LoginScreenAction
import com.shadowvault.auth.presentation.login.LoginScreenEvent
import com.shadowvault.auth.presentation.login.LoginScreenViewModel
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.Result
import com.shadowvault.core.presentation.ui.UiText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginScreenViewModelTest {

    private lateinit var viewModel: LoginScreenViewModel
    private lateinit var fakeAccountRepository: FakeAccountRepository

    @BeforeEach
    fun setup() {
        fakeAccountRepository = FakeAccountRepository()
        viewModel = LoginScreenViewModel(fakeAccountRepository)
    }

    @Test
    fun `on login button press emits OpenBrowser event on success`() = runTest {
        // Arrange
        fakeAccountRepository.tokenResult = Result.Success(
            CreateRequestTokenResult(
                success = true,
                expiresAt = "2025-12-31",
                requestToken = "test_token"
            )
        )

        val events = mutableListOf<LoginScreenEvent>()
        val job = launch { viewModel.events.toList(events) }

        viewModel.onAction(LoginScreenAction.OnLoginButtonPress)

        advanceUntilIdle()

        assertFalse(viewModel.state.value.isLoading)

        assertTrue(events.isNotEmpty())
        val event = events.first()
        assertTrue(event is LoginScreenEvent.OpenBrowser)
        if (event is LoginScreenEvent.OpenBrowser) {
            assert(event.token.contains("test_token"))
            assert(event.token.contains("movieflix://auth/redirect"))
        }

        job.cancel()
    }

    @Test
    fun `on login button press emits Error event on failure`() = runTest {
        // Arrange
        val fakeError = DataError.Network.NO_NETWORK
        fakeAccountRepository.tokenResult = Result.Error(fakeError)

        val events = mutableListOf<LoginScreenEvent>()
        val job = launch { viewModel.events.toList(events) }

        viewModel.onAction(LoginScreenAction.OnLoginButtonPress)

        advanceUntilIdle()

        assertFalse(viewModel.state.value.isLoading)

        assertTrue(events.isNotEmpty())
        val event = events.first()
        assertTrue(event is LoginScreenEvent.Error)
        if (event is LoginScreenEvent.Error) {
            val description = event.errorAlertText.description
            when (description) {
                is UiText.DynamicString -> {
                    assert(description.value.contains("desc"))
                }

                else -> fail("Expected DynamicString description")
            }
        }

        job.cancel()
    }
}
