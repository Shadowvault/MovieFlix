package com.shadowvault.movie_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadowvault.core.domain.util.fold
import com.shadowvault.core.presentation.ui.toAlertText
import com.shadowvault.movie_details.domain.GetMovieDetailsUseCase
import com.shadowvault.movie_details.domain.MovieDetailsRemoteRepository
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

class MovieDetailsScreenViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailsScreenState())
    val state = _state.onStart {
        viewModelScope.launch {
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), MovieDetailsScreenState())


    private val eventChannel = Channel<MovieDetailsScreenEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: MovieDetailsScreenAction) {
        when (action) {

            is MovieDetailsScreenAction.Init -> {
                viewModelScope.launch {
                    getMovieDetailsUseCase(action.movieId).collect { movieDetails ->
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                title = movieDetails.details.title,
                                backdropPath = movieDetails.details.backdropPath,
                                posterPath = movieDetails.details.posterPath,
                                genres = movieDetails.details.genres.map { it.name },
                                releaseDate = movieDetails.details.releaseDate,
                                runtimeMinutes = movieDetails.details.runtime,
                                starRating = movieDetails.details.voteAverage.toFloat(),
                                isFavorite = movieDetails.isFavorite,
                                description = movieDetails.details.overview ?: "",
                                cast = movieDetails.cast,
                                reviews = movieDetails.reviews,
                                similarMovies = movieDetails.similarMovies
                            )
                        }
                        if (movieDetails.errors.isNotEmpty()) {
                            movieDetails.errors.forEach { error ->
                                val alertText = error.toAlertText()
                                eventChannel.send(MovieDetailsScreenEvent.Error(alertText))
                            }
                        }
                    }
                }
            }
            else -> Unit
        }
    }


}