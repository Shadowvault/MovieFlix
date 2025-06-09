package com.shadowvault.home.presentation

import app.cash.turbine.test
import com.shadowvault.core.domain.profile.ProfileInfo
import com.shadowvault.core.domain.profile.ProfileInfoStorage
import com.shadowvault.home.domain.MovieRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {

    private val movieRepository = mockk<MovieRepository>(relaxed = true)
    private val profileInfoStorage = mockk<ProfileInfoStorage>()

    private lateinit var viewModel: HomeScreenViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { profileInfoStorage.get() } returns fakeUserProfile()
        coEvery { movieRepository.getPagedMovies(any(), any()) } returns emptyFlow()
        viewModel = HomeScreenViewModel(movieRepository, profileInfoStorage)
    }


    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `init sets userId and triggers moviesFlow`() = testScope.runTest {
        advanceUntilIdle()
        viewModel.moviesFlow.test {
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `toggleLike calls movieRepository toggleLike with toggled value`() = testScope.runTest {
        val movieId = 10
        val currentlyLiked = false

        viewModel.onAction(HomeScreenAction.OnMovieFavoritePress(movieId, currentlyLiked))

        advanceUntilIdle()

        coVerify {
            movieRepository.toggleLike(
                userId = 1,
                movieId = movieId,
                like = true
            )
        }
    }


    private fun fakeUserProfile(): ProfileInfo =
        ProfileInfo(name = "User", username = "username", 1, "")
}

