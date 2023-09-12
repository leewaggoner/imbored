package com.wreckingball.imbored.ui.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.sp
import com.wreckingball.imbored.R
import com.wreckingball.imbored.ui.activity.models.DisplayActivityState
import com.wreckingball.imbored.ui.compose.ActivityImage
import com.wreckingball.imbored.ui.theme.dimensions
import org.koin.androidx.compose.get

@Composable
fun DisplayActivity(
    boredUrl: String,
    viewModel: DisplayActivityViewModel = get(),
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
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = state.activity,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
        )

        state.imageData?.let { imageData ->
            ActivityImage(imageData = imageData)
        }

        if(state.isLoading) {
            CircularProgressIndicator()
        }

        if (state.errorMessage.isNotEmpty()) {
            AlertDialog(
                title = { Text(text = stringResource(id = R.string.error)) },
                text = { Text(text = state.errorMessage) },
                onDismissRequest = { onDismissAlert() },
                confirmButton = {
                    Button(
                        onClick = { onDismissAlert() }
                    ) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                }
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