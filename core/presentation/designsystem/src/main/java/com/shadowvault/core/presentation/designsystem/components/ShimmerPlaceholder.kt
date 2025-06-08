package com.shadowvault.core.presentation.designsystem.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

fun Modifier.shimmerPlaceholder(
    shimmerVisibility: ShimmerVisibility = ShimmerVisibility.Show
): Modifier {
    return if (shimmerVisibility == ShimmerVisibility.Hide) this else composed {
        val transition = rememberInfiniteTransition(label = "shimmer")
        val shimmerTranslateAnim by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = "shimmer-translate"
        )

        val baseColor = MaterialTheme.colorScheme.surfaceVariant
        val highlightColor = MaterialTheme.colorScheme.surface

        val brush = Brush.linearGradient(
            colors = listOf(baseColor, highlightColor, baseColor),
            start = Offset.Zero,
            end = Offset(x = shimmerTranslateAnim, y = shimmerTranslateAnim)
        )

        this
            .background(brush)
            .alpha(1f)
    }
}

sealed interface ShimmerVisibility {
    data object Show : ShimmerVisibility
    data object Hide : ShimmerVisibility
}