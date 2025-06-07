package com.shadowvault.home.presentation

import android.app.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.shadowvault.core.domain.movies.Movie
import com.shadowvault.core.presentation.designsystem.MovieFlixTheme
import com.shadowvault.core.presentation.ui.ObserveAsEvents
import com.shadowvault.home.presentation.components.MovieCard
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoot(
    viewModel: HomeScreenViewModel = koinViewModel(),
    onMovieClicked: (Int) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is HomeScreenEvent.Error -> {
                AlertDialog.Builder(context).apply {
                    setTitle(event.errorAlertText.title.asString(context))
                    setMessage(event.errorAlertText.description.asString(context))
                }.show()
            }

            else -> Unit
        }
    }

    HomeScreen(
        pagedMovies = viewModel.moviesFlow.collectAsLazyPagingItems(),
        state = state,
        onAction = { action ->
            when (action) {
                is HomeScreenAction.OnMoviePress -> {
                    onMovieClicked(action.movieId)
                }

                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun HomeScreen(
    pagedMovies: LazyPagingItems<Movie>,
    state: HomeScreenState,
    onAction: (HomeScreenAction) -> Unit,
) {
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
    ) {
        items(
            count = pagedMovies.itemCount,
            key = { index -> pagedMovies[index]?.id ?: index }) { index ->
            val movie = pagedMovies[index]
            if (movie != null) {
                MovieCard(
                    movie = movie,
                    onLikeClicked = { isLiked ->
                        onAction(HomeScreenAction.OnMovieFavoritePress(movie.id, isLiked))
                    },
                    onCardClicked = {
                        onAction(HomeScreenAction.OnMoviePress(movie.id))
                    }
                )
            }
        }

        item {
            when (pagedMovies.loadState.append) {
                is LoadState.Loading -> CircularProgressIndicator(Modifier.padding(16.dp))
                is LoadState.Error -> Text("Error loading more", color = Color.Red)
                else -> {}
            }
        }
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    val sampleMovies = listOf(
        Movie(
            id = 1,
            title = "Movie 1",
            overview = "Overview 1",
            posterPath = "",
            releaseDate = "2020-01-01",
            voteAverage = 7.8,
            genreIds = listOf(1, 2),
            isLiked = false,
            backdropPath = "",            // empty string or some dummy URL
            voteCount = 123,              // any integer number
            isAdult = false,              // boolean
            originalLanguage = "en",      // language code string
            originalTitle = "Movie 1",    // original title string
            popularity = 15.5,           // float number
            isVideo = false               // boolean
        ),
        Movie(
            id = 1,
            title = "Movie 1",
            overview = "Overview 1",
            posterPath = "",
            releaseDate = "2020-01-01",
            voteAverage = 7.8,
            genreIds = listOf(1, 2),
            isLiked = false,
            backdropPath = "",            // empty string or some dummy URL
            voteCount = 123,              // any integer number
            isAdult = false,              // boolean
            originalLanguage = "en",      // language code string
            originalTitle = "Movie 1",    // original title string
            popularity = 15.5,           // float number
            isVideo = false               // boolean
        ),
    )

    val samplePagingDataFlow = remember { MutableStateFlow(PagingData.from(sampleMovies)) }
    val lazyPagingItems = samplePagingDataFlow.collectAsLazyPagingItems()
    MovieFlixTheme {
        Surface {
            HomeScreen(
                state = HomeScreenState(
                    isLoading = false
                ),
                onAction = {},
                pagedMovies = lazyPagingItems,
            )
        }
    }
}