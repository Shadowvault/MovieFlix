package com.shadowvault.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shadowvault.core.domain.movies.Movie
import com.shadowvault.core.presentation.designsystem.MovieFlixTheme
import com.shadowvault.core.presentation.designsystem.components.LinearRatingStars

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: Movie,
    onLikeClicked: (Boolean) -> Unit,
    onCardClicked: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            )
            .clickable(onClick = onCardClicked) // Whole card clickable
    ) {
        AsyncImage(
            model = movie.backdropPath,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Gradient overlay to improve text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                        ),
                        startY = 100f
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp, end = 72.dp), // space for like icon
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                LinearRatingStars(movie.voteAverage.toFloat() / 2f)

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = movie.releaseDate,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }

        IconButton(
            onClick = { onLikeClicked(movie.isLiked) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Icon(
                imageVector = if (movie.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (movie.isLiked) "Unfavorite" else "Favorite",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Suppress("MaximumLineLength", "MaxLineLength", "MagicNumber")
@Preview
@Composable
fun MovieCardPreview() {
    MovieFlixTheme {
        Surface {
            MovieCard(
                movie = Movie(
                    id = 414906,
                    title = "The Batman",
                    overview = "In his second year of fighting crime, Batman uncovers corruption in Gotham City that connects to his own family while facing the serial killer known as the Riddler.",
                    posterPath = "https://image.tmdb.org/t/p/w500/74xTEgt7R36Fpooo50r9T25onhq.jpg",
                    backdropPath = "https://image.tmdb.org/t/p/w780/5P8SmMzSNYikXpxil6BYzJ16611.jpg",
                    releaseDate = "2022-03-01",
                    voteAverage = 7.7,
                    voteCount = 9201,
                    isAdult = false,
                    originalLanguage = "en",
                    originalTitle = "The Batman",
                    popularity = 4127.3,
                    genreIds = listOf(80, 9648, 28), // Crime, Mystery, Action
                    isVideo = false,
                    isLiked = false
                ),
                onCardClicked = {
                },
                onLikeClicked = {
                }
            )
        }
    }
}
