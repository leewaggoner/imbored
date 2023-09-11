package com.wreckingball.imbored.ui.compose

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.wreckingball.imbored.R
import com.wreckingball.imbored.domain.models.ChooseActivityImage
import com.wreckingball.imbored.ui.theme.dimensions

@Composable
fun AttributionText(
    imageData: ChooseActivityImage,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val attributionText = buildAttributionText(
        photoBy = stringResource(id = R.string.photo_by),
        on = stringResource(id = R.string.on),
        imageData = imageData,
    )
    ClickableText(
        modifier = Modifier.then(modifier),
        style = TextStyle(
            fontSize = MaterialTheme.dimensions.AttributionTextSize,
        ),
        text = attributionText,
        onClick = { offset ->
            attributionText.getStringAnnotations(
                tag = imageData.photographer,
                start = offset,
                end = offset,
            ).firstOrNull()?.let {
                uriHandler.openUri(it.item)
            }

            attributionText.getStringAnnotations(
                tag = imageData.imageHost,
                start = offset,
                end = offset,
            ).firstOrNull()?.let {
                uriHandler.openUri(it.item)
            }
        }
    )
}

private fun buildAttributionText(
    photoBy: String,
    on: String,
    imageData: ChooseActivityImage,
    ): AnnotatedString =
    buildAnnotatedString {
        append("$photoBy ")
        pushStringAnnotation(
            tag = imageData.photographer,
            annotation = imageData.photographerUrl,
        )
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append(imageData.photographer)
        }
        pop()
        append(" $on ")
        pushStringAnnotation(
            tag = imageData.imageHost,
            annotation = imageData.imageHostUrl
        )
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append(imageData.imageHost)
        }
        pop()
    }
