package com.shadowvault.movie_details.presentation

import android.app.AlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.shadowvault.core.presentation.designsystem.MovieFlixTheme
import com.shadowvault.core.presentation.designsystem.components.LinearRatingStars
import com.shadowvault.core.presentation.ui.ObserveAsEvents
import com.shadowvault.core.presentation.ui.util.shareUrl
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailsScreenRoot(
    viewModel: MovieDetailsScreenViewModel = koinViewModel(),
    movieId: Int,
    isLiked: Boolean,
    onBackButtonPress: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onAction(MovieDetailsScreenAction.Init(movieId, isLiked))
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is MovieDetailsScreenEvent.Error -> {
                AlertDialog.Builder(context).apply {
                    setTitle(event.errorAlertText.title.asString(context))
                    setMessage(event.errorAlertText.description.asString(context))
                }.show()
            }

            else -> Unit
        }
    }
    MovieDetailsScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is MovieDetailsScreenAction.OnBackButtonPress -> {
                    onBackButtonPress()
                }

                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}


@Composable
fun MovieDetailsScreen(
    state: MovieDetailsScreenState,
    onAction: (MovieDetailsScreenAction) -> Unit,
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .verticalScroll(scrollState)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box {
            AsyncImage(
                model = state.backdropPath,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
            IconButton(
                onClick = { onAction(MovieDetailsScreenAction.OnBackButtonPress) },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            IconButton(
                onClick = {
                    onAction(MovieDetailsScreenAction.OnMovieFavoritePress)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Icon(
                    imageVector = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Toggle Favorite"
                )
            }

            state.url?.let {
                IconButton(
                    onClick = {
                        shareUrl(context, it)
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                            shape = MaterialTheme.shapes.medium
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share"
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = state.title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = state.genres.joinToString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = state.releaseDate,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(8.dp))
                LinearRatingStars(state.starRating)
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Runtime: ${state.runtimeMinutes}",
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = state.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Cast",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = state.cast.joinToString { it.name },
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Reviews",
                style = MaterialTheme.typography.titleMedium
            )
            state.reviews.forEach { review ->
                Column(Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = review.author,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = review.content,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Similar Movies",
                style = MaterialTheme.typography.titleMedium
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(state.similarMovies) { similar ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(100.dp)
                    ) {
                        AsyncImage(
                            model = similar.posterPath,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Text(
                            text = similar.title,
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview
@Composable
private fun MovieDetailsScreenPreview() {
    MovieFlixTheme {
        Surface {
            MovieDetailsScreen(
                state = MovieDetailsScreenState(
                    isLoading = false
                ),
                onAction = {},
            )
        }
    }
}