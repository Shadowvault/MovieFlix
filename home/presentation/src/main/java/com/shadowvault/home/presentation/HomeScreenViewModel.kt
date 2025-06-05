package com.shadowvault.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadowvault.core.domain.util.fold
import com.shadowvault.core.presentation.ui.toAlertText
import com.shadowvault.home.domain.remote.MoviesRemote
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class HomeScreenViewModel(
    private val moviesRemote: MoviesRemote
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.onStart {
        getPopularMovies()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), HomeScreenState())


    private val eventChannel = Channel<HomeScreenEvent>()
    val events = eventChannel.receiveAsFlow()

    private val mutex = Mutex()

    private var currentPage = 1
    private var totalPages = 1

    fun onAction(action: HomeScreenAction) {
        when (action) {

            HomeScreenAction.LoadNextPopularMovies -> {
                if (currentPage <= totalPages) {
                    getPopularMovies()
                }
            }

            is HomeScreenAction.OnMovieFavoritePress -> {
                toggleLike(action.movieId)
            }

            else -> Unit
        }
    }

    private fun getPopularMovies() {
        if (currentPage == 1 || currentPage < totalPages) {
            viewModelScope.launch {
                mutex.withLock {
                    _state.update { state -> state.copy(isLoading = true) }
                    moviesRemote.getPopularMovies(currentPage).fold(
                        onSuccess = { data ->
                            _state.update { state ->
                                state.copy(
                                    popularMovies = state.popularMovies + data.movies
                                )
                            }
                            totalPages = data.totalPages
                            currentPage += 1
                        },
                        onError = { error ->
                            eventChannel.send(
                                HomeScreenEvent.Error(
                                    error.toAlertText()
                                )
                            )
                        }
                    )
                    _state.update { state -> state.copy(isLoading = false) }
                }
            }
        }
    }

    private fun toggleLike(movieId: Int) {
        _state.value = _state.value.copy(
            popularMovies = _state.value.popularMovies.map {
                if (it.id == movieId) it.copy(isLiked = !it.isLiked) else it
            }
        )
    }

}