package com.shadowvault.core.presentation.designsystem.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableText(
    text: String,
    minimizedMaxLines: Int = 4
) {
    var isExpanded by remember { mutableStateOf(false) }
    var textOverflows by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .animateContentSize() // Smooth expand/collapse
    ) {
        Column {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutResult ->
                    textOverflows = !isExpanded && textLayoutResult.hasVisualOverflow
                }
            )

            if (textOverflows || isExpanded) {
                Text(
                    text = if (isExpanded) "Show less" else "Read more",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .clickable { isExpanded = !isExpanded }
                )
            }
        }
    }
}
