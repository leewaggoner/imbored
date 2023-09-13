package com.wreckingball.imbored.ui.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

        if(state.isLoading) {
            CircularProgressIndicator()
        }

        if (state.errorMessage.isNotEmpty()) {
            BoredErrorAlert(
                message = state.errorMessage,
                onDismissAlert = onDismissAlert
            )
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