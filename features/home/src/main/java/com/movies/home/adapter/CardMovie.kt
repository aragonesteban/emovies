package com.movies.home.adapter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CardMovie(image: String, height: Dp, isInGrid: Boolean = false, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable(onClick = onClick)
            .height(height),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = if (isInGrid) ContentScale.Crop else ContentScale.Fit
        )
    }
}
