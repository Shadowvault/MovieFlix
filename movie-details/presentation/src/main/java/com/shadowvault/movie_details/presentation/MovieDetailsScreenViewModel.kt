package com.shadowvault.movie_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadowvault.core.domain.LocalMovieDataSource
import com.shadowvault.core.domain.profile.ProfileInfoStorage
import com.shadowvault.core.presentation.ui.toAlertText
import com.shadowvault.movie_details.domain.GetMovieDetailsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsScreenViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val localMovieDataSource: LocalMovieDataSource,
    private val profileInfoStorage: ProfileInfoStorage
) : ViewModel() {

    private val _userId = MutableStateFlow<Int?>(null)

    private val _state = MutableStateFlow(MovieDetailsScreenState())
    val state = _state.onStart {
        viewModelScope.launch {
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), MovieDetailsScreenState())


    private val eventChannel = Channel<MovieDetailsScreenEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val user = profileInfoStorage.get()
            _userId.value = user?.id
        }
    }

    fun onAction(action: MovieDetailsScreenAction) {
        when (action) {

            is MovieDetailsScreenAction.Init -> {
                viewModelScope.launch {
                    getMovieDetailsUseCase(action.movieId).collect { movieDetails ->
                        _state.update { state ->
                            movieDetails.details.imdbId
                            state.copy(
                                isLoading = false,
                                movieId = action.movieId,
                                title = movieDetails.details.title,
                                url = "https://www.imdb.com/title/${movieDetails.details.imdbId}",
                                backdropPath = movieDetails.details.backdropPath,
                                posterPath = movieDetails.details.posterPath,
                                genres = movieDetails.details.genres.map { it.name },
                                releaseDate = movieDetails.details.releaseDate,
                                runtimeMinutes = movieDetails.details.runtime,
                                starRating = movieDetails.details.voteAverage.toFloat() / 2f,
                                isFavorite = action.isLiked,
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
                    _userId.collect { userId ->
                        if (userId != null) {
                            observeLikeStatus(userId, action.movieId)
                        }
                    }
                }

            }

            is MovieDetailsScreenAction.OnMovieFavoritePress -> {
                toggleLike(state.value.movieId, state.value.isFavorite)
            }

            else -> Unit
        }
    }

    private fun toggleLike(movieId: Int, isCurrentlyLiked: Boolean) {
        viewModelScope.launch {
            val userId = profileInfoStorage.get()?.id ?: return@launch
            if (isCurrentlyLiked) {
                localMovieDataSource.unlikeMovie(
                    userId = userId,
                    movieId = movieId,
                )
            } else {
                localMovieDataSource.likeMovie(
                    userId = userId,
                    movieId = movieId,
                )
            }
        }
    }

    private fun observeLikeStatus(userId: Int, movieId: Int) {
        viewModelScope.launch {
            localMovieDataSource.isMovieLikedFlow(userId, movieId)
                .collect { isLiked ->
                    _state.update { it.copy(isFavorite = isLiked) }
                }
        }
    }

}