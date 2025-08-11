package com.ucasoft.kcron.ui.builder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ucasoft.kcron.kcron_ui_builder.generated.resources.*
import org.jetbrains.compose.resources.stringResource


@Composable
fun CronUiBuilder(
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier.padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        ) {
            CronField(
                stringResource(Res.string.minute),
                listOf(
                    "Every" to "*"
                )
            )
            CronField(
                stringResource(Res.string.hour)
            )
            CronField(
                stringResource(Res.string.day_of_month)
            )
            CronField(
                stringResource(Res.string.month)
            )
            CronField(
                stringResource(Res.string.day_of_week)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CronField(
    label: String,
    options: List<Pair<String, String>> = emptyList()
) {
    var open by remember { mutableStateOf(false) }

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
            onExpandedChange = { open = false }
        ) {
            TextField(
                "Label",
                onValueChange = {}
            )
            if (options.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = open,
                    onDismissRequest = { open = false }
                ) {
                    options.map {
                        DropdownMenuItem(
                            onClick = {
                                open = false
                            },
                            text = {
                                Text(it.first)
                            }
                        )
                    }
                }
            }
        }
    }
}