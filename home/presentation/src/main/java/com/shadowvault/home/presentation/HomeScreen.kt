package com.shadowvault.home.presentation

import android.app.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shadowvault.core.presentation.designsystem.MovieFlixTheme
import com.shadowvault.core.presentation.ui.ObserveAsEvents
import com.shadowvault.home.presentation.components.MovieCard
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
    state: HomeScreenState,
    onAction: (HomeScreenAction) -> Unit,
) {
    val listState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = listState,
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
    ) {
        itemsIndexed(state.popularMovies) { index, movie ->
            MovieCard(
                movie = movie,
                onLikeClicked = { onAction(HomeScreenAction.OnMovieFavoritePress(movie.id)) },
                onCardClicked = { onAction(HomeScreenAction.OnMoviePress(movie.id)) }
            )

            if (index == state.popularMovies.lastIndex) {
                onAction(HomeScreenAction.LoadNextPopularMovies)
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MovieFlixTheme {
        Surface {
            HomeScreen(
                state = HomeScreenState(
                    isLoading = false
                ),
                onAction = {},
            )
        }
    }
}