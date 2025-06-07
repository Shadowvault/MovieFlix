package com.shadowvault.core.presentation.designsystem.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shadowvault.core.presentation.designsystem.MovieFlixTheme

@Composable
fun LinearRatingStars(
    rating: Float,
    modifier: Modifier = Modifier,
    starCount: Int = 5,
    starSize: Dp = 24.dp
) {
    val colors = MaterialTheme.colorScheme
    val fillColor = colors.primary
    val backgroundColor = colors.surfaceVariant
    Row(modifier = modifier) {
        for (i in 1..starCount) {
            val fillFraction = when {
                i <= rating.toInt() -> 1f
                i == rating.toInt() + 1 -> rating % 1
                else -> 0f
            }
            StarWithFill(
                fillFraction = fillFraction,
                size = starSize,
                fillColor = fillColor,
                backgroundColor = backgroundColor
            )
        }
    }
}

@Composable
fun StarWithFill(
    fillFraction: Float,  // 0f..1f
    size: Dp,
    fillColor: Color,
    backgroundColor: Color
) {
    Canvas(modifier = Modifier.size(size)) {
        // Draw background star (unfilled)
        drawStar(color = backgroundColor, size = size.toPx())

        if (fillFraction > 0f) {
            // Clip rectangle to fill only fraction of the star width
            clipRect(right = size.toPx() * fillFraction) {
                drawStar(color = fillColor, size = size.toPx())
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawStar(color: Color, size: Float) {
    val starPath = Path().apply {
        val mid = size / 2
        val radius = size / 2
        val innerRadius = radius * 0.5f
        val angle = Math.PI / 5

        moveTo(mid, 0f)
        for (i in 1 until 10) {
            val r = if (i % 2 == 0) radius else innerRadius
            val x = mid + (r * Math.sin(i * angle)).toFloat()
            val y = mid - (r * Math.cos(i * angle)).toFloat()
            lineTo(x, y)
        }
        close()
    }
    drawPath(starPath, color = color)
}

@Preview
@Composable
fun LinearRatingStarsPreview() {
    MovieFlixTheme {
        Surface {
            LinearRatingStars(4.2f,)
        }
    }
}


