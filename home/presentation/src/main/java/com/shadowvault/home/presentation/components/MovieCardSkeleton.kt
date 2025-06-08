package com.shadowvault.home.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shadowvault.core.presentation.designsystem.components.ShimmerVisibility
import com.shadowvault.core.presentation.designsystem.components.shimmerPlaceholder

@Composable
fun MovieCardSkeleton(
    modifier: Modifier = Modifier,
    shimmerVisible: Boolean = true
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .shimmerPlaceholder(if (shimmerVisible) ShimmerVisibility.Show else ShimmerVisibility.Hide)
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp, end = 72.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(24.dp)
                    .clip(MaterialTheme.shapes.small)
                    .shimmerPlaceholder(if (shimmerVisible) ShimmerVisibility.Show else ShimmerVisibility.Hide)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 80.dp, height = 12.dp)
                        .clip(MaterialTheme.shapes.small)
                        .shimmerPlaceholder(if (shimmerVisible) ShimmerVisibility.Show else ShimmerVisibility.Hide)
                )
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(12.dp)
                        .clip(MaterialTheme.shapes.small)
                        .shimmerPlaceholder(if (shimmerVisible) ShimmerVisibility.Show else ShimmerVisibility.Hide)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
                .size(36.dp)
                .clip(MaterialTheme.shapes.medium)
                .shimmerPlaceholder(if (shimmerVisible) ShimmerVisibility.Show else ShimmerVisibility.Hide)
        )
    }
}

@Preview
@Composable
fun MovieCardSkeletonPreview() {
    MaterialTheme {
        Surface {
            MovieCardSkeleton()
        }
    }
}
