package com.wreckingball.imbored.ui.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingball.imbored.R
import com.wreckingball.imbored.ui.activity.models.DisplayActivityState
import com.wreckingball.imbored.ui.compose.ActivityImage
import com.wreckingball.imbored.ui.compose.BoredErrorAlert
import com.wreckingball.imbored.ui.theme.dimensions
import org.koin.androidx.compose.koinViewModel

@Composable
fun DisplayActivity(
    boredUrl: String,
    viewModel: DisplayActivityViewModel = koinViewModel(),
) {
    val tryAgain = stringResource(id = R.string.try_again)
    LaunchedEffect(Unit) {
        viewModel.getActivityContent(
            boredUrl = boredUrl,
            tryAgain = tryAgain
        )
    }

    DisplayActivityContent(
        state = viewModel.state,
        onDismissAlert = viewModel::onDismissAlert
    )
}

@Composable
private fun DisplayActivityContent(
    state: DisplayActivityState,
    onDismissAlert: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = MaterialTheme.dimensions.DisplayActivityMargin),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.why_dont_you),
            fontSize = MaterialTheme.dimensions.DisplayActivityTitleSize,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = state.activity,
            fontSize = MaterialTheme.dimensions.DisplayActivityBodySize,
            textAlign = TextAlign.Center,
        )

        state.imageData?.let { imageData ->
            ActivityImage(imageData = imageData)
        }

        if (state.errorMessage.isNotEmpty()) {
            BoredErrorAlert(
                message = state.errorMessage,
                onDismissAlert = onDismissAlert
            )
        }
    }

    if (state.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { },
                )
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview("Display activity content preview")
@Composable
fun DisplayActivityContentPreview() {
    DisplayActivityContent(
        state = DisplayActivityState(
            activity = "Take a flying leap."
        ),
        onDismissAlert = { }
    )
}