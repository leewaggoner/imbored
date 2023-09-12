package com.wreckingball.imbored.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.wreckingball.imbored.R
import com.wreckingball.imbored.domain.models.BoredActivityImage

@Composable
fun ActivityImage(
    imageData: BoredActivityImage,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageData.url)
            .crossfade(true)
            .crossfade(500)
            .build(),
        contentDescription = stringResource(id = R.string.image)
    )
    AttributionText(
        imageData = imageData,
    )
}

