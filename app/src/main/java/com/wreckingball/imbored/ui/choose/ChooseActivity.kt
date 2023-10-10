package com.wreckingball.imbored.ui.choose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wreckingball.imbored.R
import com.wreckingball.imbored.ui.Actions
import com.wreckingball.imbored.ui.choose.models.ChooseActivityNavigation
import com.wreckingball.imbored.ui.choose.models.ChooseActivityState
import com.wreckingball.imbored.ui.compose.ActivityImage
import com.wreckingball.imbored.ui.compose.ActivityParametersDropdown
import com.wreckingball.imbored.ui.compose.BoredErrorAlert
import com.wreckingball.imbored.ui.theme.White
import com.wreckingball.imbored.ui.theme.dimensions
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChooseActivity(
    actions: Actions,
    viewModel: ChooseActivityViewModel = koinViewModel(),
    ) {
    val tabs = stringArrayResource(id = R.array.tabs).toList()
    val participants = stringArrayResource(id = R.array.participants).toList()
    val costs = stringArrayResource(id = R.array.costs).toList()

    val navigation = viewModel.navigation.collectAsStateWithLifecycle(null)
    navigation.value?.let { nav ->
        when (nav) {
            is ChooseActivityNavigation.DisplayActivity -> {
                actions.navigateToDisplay(nav.boredUrl)
            }
        }
    }

    LaunchedEffect(Unit) {
        //load initial image only once per session
        val tabIndex = viewModel.state.selectedTabIndex
        viewModel.onTabClick(
            tabIndex = tabIndex,
            name = tabs[tabIndex]
        )
    }

    ChooseActivityContent(
        state = viewModel.state,
        tabs = tabs,
        onTabClick = viewModel::onTabClick,
        participants = participants,
        onParticipantsSelected = viewModel::onParticipantsSelected,
        costs = costs,
        onCostSelected = viewModel::onCostSelected,
        onDisplayActivity = viewModel::onDisplayActivity,
        onDismissAlert = viewModel::onDismissAlert
    )
}

@Composable
fun ChooseActivityContent(
    state: ChooseActivityState,
    tabs: List<String>,
    onTabClick: (Int, String) -> Unit,
    participants: List<String>,
    onParticipantsSelected: (Int, String) -> Unit,
    costs: List<String>,
    onCostSelected: (Int, String) -> Unit,
    onDisplayActivity: () -> Unit,
    onDismissAlert: () -> Unit,
    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        ScrollableTabRow(
            modifier = Modifier
                .fillMaxWidth(),
            selectedTabIndex = state.selectedTabIndex,
            contentColor = White,
            containerColor = MaterialTheme.colorScheme.tertiary,
            edgePadding = 0.dp,
        ) {
            tabs.forEachIndexed { tabIndex, tab ->
                Tab(
                    selected = state.selectedTabIndex == tabIndex,
                    onClick = {
                        onTabClick(tabIndex, tab)
                      },
                    text = { Text(text = tab) }
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimensions.ChooseActivityMargin)
                .weight(1.0f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.ChooseActivitySpacer))

            ActivityParameters(
                state = state,
                participants = participants,
                onParticipantsSelected = onParticipantsSelected,
                costs = costs,
                onCostSelected = onCostSelected,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.ChooseActivitySmallSpacer))
            state.imageData?.let { imageData ->
                ActivityImage(imageData = imageData)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.dimensions.ChooseActivityMargin),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                modifier = Modifier
                    .width(160.dp),
                onClick = {
                    onDisplayActivity()
                },
            ) {
                Text(text = stringResource(id = R.string.go))
            }
        }

        if (state.errorMessage.isNotEmpty()) {
            BoredErrorAlert(
                message = state.errorMessage,
                onDismissAlert = onDismissAlert
            )
        }
    }
}

@Composable
private fun ActivityParameters(
    state: ChooseActivityState,
    participants: List<String>,
    onParticipantsSelected: (Int, String) -> Unit,
    costs: List<String>,
    onCostSelected: (Int, String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        ActivityParametersDropdown(
            label = stringResource(id = R.string.participants),
            selectedItem = participants[state.selectedParticipantsIndex],
            menuItems = participants,
            onItemSelected = onParticipantsSelected
        )

        ActivityParametersDropdown(
            label = stringResource(id = R.string.cost),
            selectedItem = costs[state.selectedCostIndex],
            menuItems = costs,
            onItemSelected = onCostSelected
        )
    }
}

@Composable
@Preview(name = "ChooseActivityContent Preview")
fun ChooseActivityContentPreview() {
    ChooseActivityContent(
        state = ChooseActivityState(),
        participants = listOf("One", "Two", "Three"),
        tabs = listOf("tab1", "tab2", "tab3"),
        onTabClick = { _, _ -> },
        onParticipantsSelected = { _, _ -> },
        costs = listOf("Cheap", "Expensive"),
        onCostSelected = { _, _ -> },
        onDisplayActivity = { },
        onDismissAlert = { },
    )
}
