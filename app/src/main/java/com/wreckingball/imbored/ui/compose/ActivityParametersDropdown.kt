package com.wreckingball.imbored.ui.compose

import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingball.imbored.ui.theme.dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityParametersDropdown(
    label: String,
    selectedItem: String,
    menuItems: List<String>,
    onItemSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded

        }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .focusProperties {
                    canFocus = false
                }
                .width(MaterialTheme.dimensions.APDropdownWidth),
            value = selectedItem,
            onValueChange = { },
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            readOnly = true,
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            menuItems.forEach {item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        expanded = false
                        onItemSelected(item)
                    }
                )
            }
        }
    }
}

@Preview(name = "ActivityParameterDropdown preview")
@Composable
fun ActivityParametersDropdownPreview() {
    ActivityParametersDropdown(
        label = "Items",
        selectedItem = "",
        menuItems = listOf("One", "Two", "Three"),
        onItemSelected = { }
    )
}