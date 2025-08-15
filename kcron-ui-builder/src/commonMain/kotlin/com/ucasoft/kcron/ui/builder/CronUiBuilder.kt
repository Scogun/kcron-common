package com.ucasoft.kcron.ui.builder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ucasoft.kcron.Cron
import com.ucasoft.kcron.core.builders.Builder
import com.ucasoft.kcron.core.common.CronPart
import com.ucasoft.kcron.core.common.PartValue
import com.ucasoft.kcron.core.common.WeekDays
import com.ucasoft.kcron.core.exceptions.WrongPartCombinations
import com.ucasoft.kcron.core.exceptions.WrongPartExpression
import com.ucasoft.kcron.core.parsers.Parser
import com.ucasoft.kcron.kcron_ui_builder.generated.resources.*
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CronUiBuilder(
    expression: String = "* * * * *",
    modifier: Modifier = Modifier,
    allowCustom: Boolean = true,
    firstDayOfWeek: WeekDays = WeekDays.Monday,
    onBuild: (Builder<*, *, *>) -> Unit = {},
) {
    val parser = Parser()
    var parts by remember { mutableStateOf(parser.parse(expression).parts) }

    Card(
        modifier = modifier.padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var daysOfWeek = stringArrayResource(Res.array.days_of_week).withIndex().map { it.value to it.index }
            daysOfWeek = daysOfWeek.drop(firstDayOfWeek.ordinal) + daysOfWeek.take(firstDayOfWeek.ordinal)
           listOf(
                Res.string.minute to listOf(
                    pluralStringResource(Res.plurals.every, 1) to "*",
                    "Hour start" to "0",
                    "15" to "15",
                    "30" to "30",
                    "45" to "45",
                    pluralStringResource(Res.plurals.every, 5, 5) to "0/5",
                    pluralStringResource(Res.plurals.every, 15, 15) to "0/15",
                    pluralStringResource(Res.plurals.every, 30, 30) to "0/30"
                ),
                Res.string.hour to listOf(
                    pluralStringResource(Res.plurals.every, 1) to "*",
                    "Midnight" to "0",
                    "6 AM" to "6",
                    "9 AM" to "9",
                    "Noon" to "12",
                    "6 PM" to "18",
                    "9 PM" to "21",
                    pluralStringResource(Res.plurals.every, 2, 2) to "0/2",
                    pluralStringResource(Res.plurals.every, 6, 6) to "0/6",
                    pluralStringResource(Res.plurals.every, 12, 12) to "0/12"
                ),
                Res.string.day_of_month to listOf(
                    pluralStringResource(Res.plurals.every, 1) to "*",
                    "1st" to "1",
                    "15th" to "15",
                    "Last" to "L",
                    "First Weekday" to "1W",
                    "Last Weekday" to "LW",
                    pluralStringResource(Res.plurals.every, 2, 2) to "1/2",
                    pluralStringResource(Res.plurals.every, 7, 7) to "1/7",
                    pluralStringResource(Res.plurals.every, 14, 14) to "1/14"
                ),
                Res.string.month to listOf(
                    pluralStringResource(Res.plurals.every, 1) to "*"
                ) + stringArrayResource(Res.array.months).withIndex().map { it.value to (it.index + 1).toString() },
                Res.string.day_of_week to listOf(
                    pluralStringResource(Res.plurals.every, 1) to "*"
                ) + daysOfWeek.withIndex().map { (index, name) -> name.first to (index + 1).toString() } +
                listOf(
                    "Weekdays" to "1-5",
                    "Weekends" to "6-7"
                )
            ).map {
               it.first to if (allowCustom)
                   it.second + ("Custom" to "")
               else
                   it.second
           }.map {
                (labelRes, options) ->
                var isError by remember { mutableStateOf(false) }
                CronField(
                    stringResource(labelRes),
                    when(labelRes) {
                        Res.string.minute -> parts[CronPart.Minutes]!!.value
                        Res.string.hour -> parts[CronPart.Hours]!!.value
                        Res.string.day_of_month -> parts[CronPart.Days]!!.value
                        Res.string.month -> parts[CronPart.Months]!!.value
                        Res.string.day_of_week -> parts[CronPart.DaysOfWeek]!!.value
                        else -> ""
                    },
                    options,
                    isError = isError
                ) {
                    try {
                        val copy = when (labelRes) {
                            Res.string.minute -> copyParts(parts, minutes = it)
                            Res.string.hour -> copyParts(parts, hours = it)
                            Res.string.day_of_month -> copyParts(parts, dayOfMonth = it)
                            Res.string.month -> copyParts(parts, month = it)
                            Res.string.day_of_week -> copyParts(parts, dayOfWeek = it)
                            else -> parts
                        }
                        parser.parse(copy.entries.joinToString(" ") { it.value.value })
                        parts = copy
                        isError = false
                    } catch (wrong: WrongPartExpression) {
                        println(wrong)
                        isError = true
                    } catch (wrong: WrongPartCombinations) {
                        throw wrong
                    }
                }
            }
            Spacer(
                Modifier.weight(1f)
            )
            OutlinedTextField(
                parts.entries.drop(1).take(5).joinToString(" ") { it.value.value },
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onPrimary, unfocusedTextColor = MaterialTheme.colorScheme.onPrimary),
                textStyle = TextStyle.Default.copy(textAlign = TextAlign.Center)
            )
            Spacer(
                Modifier.weight(1f)
            )
            Row {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {},
                    ) {
                        Text("Cancel")
                    }
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            onBuild(Cron.parseAndBuild(parts.entries.joinToString(" ") { it.value.value }) {
                                it.firstDayOfWeek = firstDayOfWeek
                            })
                        },
                    ) {
                        Text("Ok")
                    }
                }
            }
        }
    }
}

private fun copyParts(
    parts: Map<CronPart, PartValue>,
    minutes: String? = null,
    hours: String? = null,
    dayOfMonth: String? = null,
    month: String? = null,
    dayOfWeek: String? = null
) : Map<CronPart, PartValue> {
    return parts.entries.associate {
        it.key to when (it.key) {
            CronPart.Minutes -> PartValue(it.value.type, minutes ?: it.value.value)
            CronPart.Hours -> PartValue(it.value.type, hours ?: it.value.value)
            CronPart.Days -> PartValue(it.value.type, dayOfMonth ?: it.value.value)
            CronPart.Months -> PartValue(it.value.type, month ?: it.value.value)
            CronPart.DaysOfWeek -> PartValue(it.value.type, dayOfWeek ?: it.value.value)
            else -> it.value
        }
    }
}