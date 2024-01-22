package com.ucasoft.kcron.core.parsers

import com.ucasoft.kcron.core.common.*
import com.ucasoft.kcron.core.exceptions.*
import com.ucasoft.kcron.core.settings.Version

class Parser {

    private val partParsers = listOf(
        SecondsMinutesParser(CronPart.Seconds),
        SecondsMinutesParser(CronPart.Minutes),
        HoursParser(),
        DaysParser(),
        MonthsParser(),
        DaysOfWeekParser(),
        YearsParser()
    )

    private val combinationRules = listOf(
        CombinationRule(CronPart.Days, DayGroups.Specific, listOf(CombinationRule(CronPart.DaysOfWeek, DayOfWeekGroups.Any))),
        CombinationRule(CronPart.Days, DayGroups.EveryStartingAt, listOf(CombinationRule(CronPart.DaysOfWeek, DayOfWeekGroups.Any))),
        CombinationRule(CronPart.Days, DayGroups.LastDay, listOf(CombinationRule(CronPart.DaysOfWeek, DayOfWeekGroups.Any))),
        CombinationRule(CronPart.Days, DayGroups.LastWeekday, listOf(CombinationRule(CronPart.DaysOfWeek, DayOfWeekGroups.Any))),
        CombinationRule(CronPart.Days, DayGroups.BeforeTheEnd, listOf(CombinationRule(CronPart.DaysOfWeek, DayOfWeekGroups.Any))),
        CombinationRule(CronPart.Days, DayGroups.NearestWeekday, listOf(CombinationRule(CronPart.DaysOfWeek, DayOfWeekGroups.Any))),
        CombinationRule(CronPart.DaysOfWeek, DayOfWeekGroups.Specific, listOf(CombinationRule(CronPart.Days, DayGroups.Any))),
        CombinationRule(CronPart.DaysOfWeek, DayOfWeekGroups.EveryStartingAt, listOf(CombinationRule(CronPart.Days, DayGroups.Any))),
        CombinationRule(CronPart.DaysOfWeek, DayOfWeekGroups.Last, listOf(CombinationRule(CronPart.Days, DayGroups.Any))),
        CombinationRule(CronPart.DaysOfWeek, DayOfWeekGroups.OfMonth, listOf(CombinationRule(CronPart.Days, DayGroups.Any)))
    )

    fun parse(expression: String, version: Version = Version.Auto): ParseResult {
        val expressionParts = splitExpression(expression, version)
        val parseResult = parsePartsAndEnsureValid(expressionParts)
        ensureCombinationRules(parseResult.parts)
        return parseResult
    }

    private fun splitExpression(expression: String, version: Version): List<String> {
        val expressionParts = expression.split(' ')
        if (expressionParts.size == 5 && (version == Version.Auto || version == Version.Classic)) {
            return expressionParts.toMutableList().also {
                it.add(0, "0")
                it.add("*")
            }
        }

        if (expressionParts.size == 7 && (version == Version.Auto || version == Version.Modern)) {
            return expressionParts
        }

        throw WrongCronExpression(expression, version)
    }

    private fun parsePartsAndEnsureValid(expressionParts: List<String>): ParseResult {
        val partExceptions = mutableListOf<WrongPartExpression>()
        for ((index, partValue) in expressionParts.withIndex()) {
            val specificParser = partParsers[index]
            specificParser.parse(partValue)
            if (!specificParser.isValid) {
                partExceptions.add(WrongPartExpression(partValue, specificParser))
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
            result.parts[parser.part] = PartValue(parser.group as CronGroups, expressionParts[index])
        }
        return result
    }

    private fun ensureCombinationRules(parts: MutableMap<CronPart, PartValue>) {
        val combinationExceptions = mutableListOf<WrongPartCombination>()
        for (part in parts) {
            val rule = combinationRules.firstOrNull { r -> r.part == part.key && r.type == part.value.type }
            if (rule != null) {
                for (dependency in rule.dependencies!!) {
                    val secondPart = parts[dependency.part]
                    if (secondPart!!.type != dependency.type) {
                        combinationExceptions.add(
                            WrongPartCombination(
                                part,
                                dependency,
                                secondPart
                            )
                        )
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