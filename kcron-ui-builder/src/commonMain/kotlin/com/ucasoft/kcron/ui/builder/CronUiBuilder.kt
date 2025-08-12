package com.ucasoft.kcron.ui.builder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ucasoft.kcron.core.common.WeekDays
import com.ucasoft.kcron.core.exceptions.WrongPartExpression
import com.ucasoft.kcron.core.parsers.Parser
import com.ucasoft.kcron.core.settings.Version
import com.ucasoft.kcron.kcron_ui_builder.generated.resources.*
import org.jetbrains.compose.resources.stringResource

private data class Expression(
    val minutes: String = "*",
    val hours: String = "*",
    val dayOfMonth: String = "?",
    val month: String = "*",
    val dayOfWeek: String = "*",
) {
    override fun toString(): String {
        return "$minutes $hours $dayOfMonth $month $dayOfWeek"
    }
}

@Composable
fun CronUiBuilder(
    modifier: Modifier = Modifier,
    allowCustom: Boolean = true,
    firstDayOfWeek: WeekDays = WeekDays.Monday
) {
    val parser = Parser()
    var expression by remember { mutableStateOf(Expression()) }

    Card(
        modifier = modifier.padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CronField(
                stringResource(Res.string.minute),
                listOf(
                    "Every" to "*",
                    "Hour start" to "0",
                    "15" to "15",
                    "30" to "30",
                    "45" to "45",
                    "Every 5" to "0/5",
                    "Every 15" to "0/15",
                    "Every 30" to "0/30"
                ),
                allowCustom
            ) {
                try {
                    val copy = expression.copy(minutes = it)
                    parser.parse(copy.toString(), Version.Classic)
                    expression = copy
                }
                catch (wrong: WrongPartExpression) {
                    println(wrong)
                }
            }
            CronField(
                stringResource(Res.string.hour),
                listOf(
                    "Every" to "*",
                    "Midnight" to "0",
                    "6 AM" to "6",
                    "9 AM" to "9",
                    "Noon" to "12",
                    "6 PM" to "18",
                    "9 PM" to "21",
                    "Every 2" to "0/2",
                    "Every 6" to "0/6",
                    "Every 12" to "0/12"
                ),
                allowCustom
            ) {
                try {
                    val copy = expression.copy(hours = it)
                    parser.parse(copy.toString(), Version.Classic)
                    expression = copy
                }
                catch (wrong: WrongPartExpression) {
                    println(wrong)
                }
            }
            CronField(
                stringResource(Res.string.day_of_month),
                listOf(
                    "Every" to "*",
                    "1st" to "1",
                    "15th" to "15",
                    "Last" to "L",
                    "First Weekday" to "1W",
                    "Last Weekday" to "LW",
                    "Every 2" to "*/2",
                    "Every 7" to "*/7",
                    "Every 14" to "*/14"
                ),
                allowCustom
            )
            CronField(
                stringResource(Res.string.month),
                listOf(
                    "Every" to "*",
                    "January" to "1",
                    "February" to "2",
                    "March" to "3",
                    "April" to "4",
                    "May" to "5",
                    "June" to "6",
                    "July" to "7",
                    "August" to "8",
                    "September" to "9",
                    "October" to "10",
                    "November" to "11",
                    "December" to "12"
                ),
                allowCustom
            )
            CronField(
                stringResource(Res.string.day_of_week),
                listOf(
                    "Every" to "*",
                    "Monday" to "1",
                    "Tuesday" to "2",
                    "Wednesday" to "3",
                    "Thursday" to "4",
                    "Friday" to "5",
                    "Saturday" to "6",
                    "Sunday" to "7",
                    "Weekdays" to "1-5",
                    "Weekends" to "6-7"
                ),
                allowCustom
            )
            Spacer(
                Modifier.weight(1f)
            )
            OutlinedTextField(
                expression.toString(),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onPrimary, unfocusedTextColor = MaterialTheme.colorScheme.onPrimary),
                textStyle = TextStyle.Default.copy(textAlign = TextAlign.Center)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CronField(
    label: String,
    options: List<Pair<String, String>> = emptyList(),
    allowCustom: Boolean = false,
    customPlaceholder: String = "Enter custom value",
    onValueChanged: (String) -> Unit = {}
) {
    var open by remember { mutableStateOf(false) }
    var prebuildPattern by remember { mutableStateOf(options.firstOrNull()) }
    val customPattern = "Custom" to ""
    var selectedPattern by remember { mutableStateOf("") }

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
                prebuildPattern?.first ?: "",
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(open)
                },
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
            )
            if(options.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = open,
                    onDismissRequest = { open = false }
                ) {
                    options.map {
                        DropdownMenuItem(
                            text = { Text(it.first) },
                            onClick = {
                                prebuildPattern = it
                                selectedPattern = it.second
                                onValueChanged(it.second)
                                open = false
                            }
                        )
                    }
                    if (allowCustom) {
                        DropdownMenuItem(
                            text = { Text(customPattern.first) },
                            onClick = {
                                prebuildPattern = customPattern
                                selectedPattern = customPattern.second
                                open = false
                            }
                        )
                    }
                }
            }
        }
        if (prebuildPattern == customPattern) {
            OutlinedTextField(
                selectedPattern,
                onValueChange = {
                    selectedPattern = it
                    onValueChanged(it)
                },
                placeholder = { Text(customPlaceholder) }
            )
        }
    }
}