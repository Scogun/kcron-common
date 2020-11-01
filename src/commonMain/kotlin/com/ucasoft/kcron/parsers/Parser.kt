package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.CronGroups
import com.ucasoft.kcron.common.DayGroups
import com.ucasoft.kcron.common.DayOfWeekGroups
import com.ucasoft.kcron.exceptions.*

class Parser {

    private val partParsers = listOf(
        SecondsMinutesParser("seconds"),
        SecondsMinutesParser("minutes"),
        HoursParser(),
        DaysParser(),
        MonthsParser(),
        DaysOfWeekParser(),
        YearsParser()
    )

    private val combinationRules = listOf(
        CombinationRule("days", DayGroups.Specific, listOf(CombinationRule("days of the week", DayOfWeekGroups.Any))),
        CombinationRule("days", DayGroups.EveryStartingAt, listOf(CombinationRule("days of the week", DayOfWeekGroups.Any))),
        CombinationRule("days", DayGroups.LastDay, listOf(CombinationRule("days of the week", DayOfWeekGroups.Any))),
        CombinationRule("days", DayGroups.LastWeekday, listOf(CombinationRule("days of the week", DayOfWeekGroups.Any))),
        CombinationRule("days", DayGroups.BeforeTheEnd, listOf(CombinationRule("days of the week", DayOfWeekGroups.Any))),
        CombinationRule("days", DayGroups.NearestWeekday, listOf(CombinationRule("days of the week", DayOfWeekGroups.Any))),
        CombinationRule("days of the week", DayOfWeekGroups.Specific, listOf(CombinationRule("days", DayGroups.Any))),
        CombinationRule("days of the week", DayOfWeekGroups.EveryStartingAt, listOf(CombinationRule("days", DayGroups.Any))),
        CombinationRule("days of the week", DayOfWeekGroups.Last, listOf(CombinationRule("days", DayGroups.Any))),
        CombinationRule("days of the week", DayOfWeekGroups.OfMonth, listOf(CombinationRule("days", DayGroups.Any)))
    )

    fun parse(expression: String): ParseResult {
        val expressionParts = splitExpression(expression)
        val parseResult = parsePartsAndEnsureValid(expressionParts)
        ensureCombinationRules(parseResult.parts)
        return parseResult
    }

    private fun splitExpression(expression: String): List<String> {
        val expressionParts = expression.split(' ')
        if (expressionParts.size != 7) {
            throw WrongCronExpression(expression)
        }
        return expressionParts
    }

    private fun parsePartsAndEnsureValid(expressionParts: List<String>): ParseResult {
        val partExceptions = mutableListOf<WrongPartExpression>()
        for ((index, part) in expressionParts.withIndex()) {
            val specificParser = partParsers[index]
            specificParser.parse(part)
            if (!specificParser.isValid) {
                partExceptions.add(WrongPartExpression(part, specificParser))
            }
        }

        if (partExceptions.size == 1) {
            throw partExceptions[0]
        }

        if (partExceptions.size > 1) {
            throw WrongPartsExpression(partExceptions)
        }

        val result = ParseResult()
        for ((index, parser) in partParsers.withIndex()) {
            result.parts[parser.partName] = PartResult(parser.group as CronGroups, expressionParts[index])
        }
        return result
    }

    private fun ensureCombinationRules(parts: MutableMap<String, PartResult>) {
        val combinationExceptions = mutableListOf<WrongPartCombination>()
        for (part in parts) {
            val rule = combinationRules.firstOrNull { r -> r.partName == part.key && r.type == part.value.type }
            if (rule != null) {
                for (dependency in rule.dependencies!!) {
                    val secondPart = parts[dependency.partName]
                    if (secondPart!!.type != dependency.type) {
                        combinationExceptions.add(WrongPartCombination(part, dependency, secondPart))
                    }
                }
            }
        }

        if (combinationExceptions.size == 1) {
            throw combinationExceptions[0]
        }

        if (combinationExceptions.size > 1) {
            throw WrongPartCombinations(combinationExceptions)
        }
    }
}