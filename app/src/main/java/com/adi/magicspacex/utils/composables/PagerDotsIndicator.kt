package com.adi.magicspacex.utils.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Displays a row of dot indicators to highlight the current selection in a pager.
 */
@Composable
internal fun PagerDotsIndicator(
    totalNumberOfItems: Int,
    selectedIndex: Int,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.secondary,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(totalNumberOfItems) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(
                        if (it == selectedIndex) {
                            selectedColor
                        } else {
                            unselectedColor
                        }
                    )
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF
)
@Composable
private fun PreviewPagerDotsIndicator() {
    PagerDotsIndicator(
        totalNumberOfItems = 3,
        selectedIndex = 2,
    )
}
