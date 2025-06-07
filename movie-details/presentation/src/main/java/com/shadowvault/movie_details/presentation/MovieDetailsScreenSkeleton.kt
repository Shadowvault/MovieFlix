package com.shadowvault.movie_details.presentation

import androidx.compose.animation.core.*
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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MovieDetailsScreenSkeleton() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .verticalScroll(scrollState)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Backdrop placeholder with shimmer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(0.dp))
                .shimmerPlaceholder(show = true)
        )

        Spacer(Modifier.height(16.dp))

        // Title placeholder
        Box(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth(0.7f)
                .clip(RoundedCornerShape(4.dp))
                .shimmerPlaceholder(show = true)
        )

        Spacer(Modifier.height(8.dp))

        // Genres placeholder
        Box(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth(0.5f)
                .clip(RoundedCornerShape(4.dp))
                .shimmerPlaceholder(show = true)
        )

        Spacer(Modifier.height(16.dp))

        // Release date + stars row placeholders
        Row {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerPlaceholder(show = true)
            )
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerPlaceholder(show = true)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Runtime placeholder
        Box(
            modifier = Modifier
                .height(20.dp)
                .width(120.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerPlaceholder(show = true)
        )

        Spacer(Modifier.height(24.dp))

        // Section title placeholders and content placeholders
        listOf("Description", "Cast", "Reviews", "Similar Movies").forEach { _ ->
            Box(
                modifier = Modifier
                    .height(24.dp)
                    .fillMaxWidth(0.3f)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerPlaceholder(show = true)
            )
            Spacer(Modifier.height(8.dp))

            repeat(3) {
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerPlaceholder(show = true)
                )
                Spacer(Modifier.height(4.dp))
            }

            Spacer(Modifier.height(24.dp))
        }

        // Similar movies horizontal row placeholders
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            items(5) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(100.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .shimmerPlaceholder(show = true)
                    )
                    Spacer(Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth(0.7f)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerPlaceholder(show = true)
                    )
                }
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}

fun Modifier.shimmerPlaceholder(
    show: Boolean = true,
    baseColor: Color = Color.Gray.copy(alpha = 0.2f),
    highlightColor: Color = Color.White.copy(alpha = 0.4f)
): Modifier {
    return if (!show) this else composed {
        val transition = rememberInfiniteTransition(label = "shimmer")
        val shimmerTranslateAnim by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = "shimmer-translate"
        )

        val brush = Brush.linearGradient(
            colors = listOf(baseColor, highlightColor, baseColor),
            start = Offset.Zero,
            end = Offset(x = shimmerTranslateAnim, y = shimmerTranslateAnim)
        )

        this.background(brush).alpha(1f)
    }
}

