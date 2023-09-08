package com.example.imbored.ui.choose

import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.imbored.ui.Actions
import com.example.imbored.ui.theme.White
import org.koin.androidx.compose.get

@Composable
fun ChooseActivity(
    actions: Actions,
    viewModel: ChooseActivityViewModel = get(),
    ) {

    val tabs = viewModel.tabs.map { stringResource(id = it) }

    ChooseActivityContent(
        tabs,
        viewModel::onTabClick,
    )
}

@Composable
fun ChooseActivityContent(
    tabs: List<String>,
    onTabClick: (Int) -> Unit,
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
                onClick = { selectedTabIndex = tabIndex },
                text = { Text(text = tab) }
            )
        }
    }
}

@Composable
@Preview
fun ChooseActivityContentPreview() {
    ChooseActivityContent(
        tabs = listOf("tab1", "tab2", "tab3"),
        onTabClick = { },
    )
}
