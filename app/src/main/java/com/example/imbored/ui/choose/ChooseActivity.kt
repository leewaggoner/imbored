package com.example.imbored.ui.choose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.imbored.R
import com.example.imbored.ui.Actions
import com.example.imbored.ui.choose.models.ChooseActivityState
import com.example.imbored.ui.compose.ActivityParametersDropdown
import com.example.imbored.ui.theme.White
import org.koin.androidx.compose.get

@Composable
fun ChooseActivity(
    actions: Actions,
    viewModel: ChooseActivityViewModel = get(),
    ) {
    val tabs = viewModel.tabs.map { stringResource(id = it) }
    val participants = viewModel.participants.map { stringResource(id = it) }
    val costs = viewModel.costs.map { stringResource(id = it) }

    ChooseActivityContent(
        state = viewModel.state,
        tabs = tabs,
        onTabClick = viewModel::onTabClick,
        participants = participants,
        onParticipantsSelected = viewModel::onParticipantsSelected,
        costs = costs,
        onCostSelected = viewModel::onCostSelected,
    )
}

@Composable
fun ChooseActivityContent(
    state: ChooseActivityState,
    tabs: List<String>,
    onTabClick: (Int) -> Unit,
    participants: List<String>,
    onParticipantsSelected: (String) -> Unit,
    costs: List<String>,
    onCostSelected: (String) -> Unit,
    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        ScrollableTabRow(
            modifier = Modifier
                .fillMaxWidth(),
            selectedTabIndex = selectedTabIndex,
            contentColor = White,
            containerColor = MaterialTheme.colorScheme.tertiary,
            edgePadding = 0.dp,
        ) {
            tabs.forEachIndexed { tabIndex, tab ->
                Tab(
                    selected = selectedTabIndex == tabIndex,
                    onClick = {
                        selectedTabIndex = tabIndex
                        onTabClick(tabIndex)
                      },
                    text = { Text(text = tab) }
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            ActivityParameters(
                state = state,
                participants = participants,
                onParticipantsSelected = onParticipantsSelected,
                costs = costs,
                onCostSelected = onCostSelected,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActivityParameters(
    state: ChooseActivityState,
    participants: List<String>,
    onParticipantsSelected: (String) -> Unit,
    costs: List<String>,
    onCostSelected: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActivityParametersDropdown(
            label = stringResource(id = R.string.participants),
            selectedItem = state.selectedParticipants,
            menuItems = participants,
            onItemSelected = onParticipantsSelected
        )

        ActivityParametersDropdown(
            label = stringResource(id = R.string.cost),
            selectedItem = state.selectedCost,
            menuItems = costs,
            onItemSelected = onCostSelected
        )
    }
}

@Composable
@Preview
fun ChooseActivityContentPreview() {
    ChooseActivityContent(
        state = ChooseActivityState(
            selectedParticipants = "",
            selectedCost = ""
        ),
        participants = listOf("One", "Two", "Three"),
        tabs = listOf("tab1", "tab2", "tab3"),
        onTabClick = { },
        onParticipantsSelected = { },
        costs = listOf("Cheap", "Expensive"),
        onCostSelected = { },
    )
}
