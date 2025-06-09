package com.shadowvault.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.shadowvault.core.domain.profile.ProfileInfoStorage
import com.shadowvault.home.domain.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModel(
    private val movieRepository: MovieRepository,
    private val profileInfoStorage: ProfileInfoStorage
) : ViewModel() {

    private val _userId = MutableStateFlow<Int?>(null)

    val moviesFlow = _userId.filterNotNull()
        .flatMapLatest { userId ->
            movieRepository.getPagedMovies(userId, viewModelScope)
        }.cachedIn(viewModelScope)

    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    private val eventChannel = Channel<HomeScreenEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val user = profileInfoStorage.get()
            _userId.value = user?.id
        }
    }

    fun onAction(action: HomeScreenAction) {
        when (action) {
            is HomeScreenAction.OnMovieFavoritePress -> {
                toggleLike(action.movieId, action.isLiked)
            }

            is HomeScreenAction.OnForceRefresh -> {
                viewModelScope.launch {
                    _state.update { state -> state.copy(isRefreshing = true) }
                    movieRepository.clear()
                    action.pagedMovies.refresh()
                }
            }

            HomeScreenAction.OnRefreshStateDone -> {
                _state.update { state -> state.copy(isRefreshing = false) }
            }

            else -> Unit
        }
    }

    private fun toggleLike(movieId: Int, isCurrentlyLiked: Boolean) {
        viewModelScope.launch {
            val userId = profileInfoStorage.get()?.id ?: return@launch
            movieRepository.toggleLike(
                userId = userId,
                movieId = movieId,
                like = !isCurrentlyLiked
            )
        }
    }
}
