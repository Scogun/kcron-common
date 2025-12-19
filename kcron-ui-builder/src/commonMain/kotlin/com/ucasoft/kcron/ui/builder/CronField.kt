package com.ucasoft.kcron.ui.builder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CronField(
    label: String,
    value: String,
    options: List<Pair<String, String>> = emptyList(),
    customPlaceholder: String = "Enter custom value",
    isError: Boolean = false,
    onValueChanged: (String) -> Unit = {}
) {
    var open by remember { mutableStateOf(false) }
    var prebuildPattern by remember { mutableStateOf(options.firstOrNull { it.second == value } ?: options.last()) }
    var selectedPattern by remember { mutableStateOf(value) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        ExposedDropdownMenuBox(
            expanded = open,
            onExpandedChange = { open = it },
        ) {
            OutlinedTextField(
                prebuildPattern.first,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(open)
                },
                modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
            )
            if (options.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = open,
                    onDismissRequest = { open = false }
                ) {
                    options.forEach {
                        DropdownMenuItem(
                            text = { Text(it.first) },
                            onClick = {
                                prebuildPattern = it
                                if (it.second.isNotEmpty()) {
                                    selectedPattern = it.second
                                    onValueChanged(it.second)
                                }
                                open = false
                            }
                        )
                    }
                }
            }
        }
        if (prebuildPattern.second == "") {
            OutlinedTextField(
                selectedPattern,
                onValueChange = {
                    selectedPattern = it
                    onValueChanged(it)
                },
                placeholder = { Text(customPlaceholder) },
                isError = isError
            )
        }
    }
}