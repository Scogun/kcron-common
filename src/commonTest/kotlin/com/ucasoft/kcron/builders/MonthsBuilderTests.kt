package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.MonthGroups
import io.kotest.matchers.collections.shouldContainExactly
import kotlin.test.BeforeTest
import kotlin.test.Test

class MonthsBuilderTests {

    private lateinit var builder: MonthsBuilder

    @BeforeTest
    fun setupOnce() {
        builder = MonthsBuilder()
    }

    @Test
    fun buildMonths() {
        builder.build(MonthGroups.Any, "*")
        builder.months.shouldContainExactly(listOf(1..12).flatten())
        builder.build(MonthGroups.Specific, "1,2,6,10,12")
        builder.months.shouldContainExactly(1, 2, 6, 10, 12)
        builder.build(MonthGroups.Specific, "JAN,FEB,JUN,OCT,DEC")
        builder.months.shouldContainExactly(1, 2, 6, 10, 12)
        builder.build(MonthGroups.EveryStartingAt, "3/2")
        builder.months.shouldContainExactly(3, 5, 7, 9, 11)
        builder.build(MonthGroups.EveryBetween, "6-8")
        builder.months.shouldContainExactly(6, 7, 8)
        builder.build(MonthGroups.Any, "*")
        builder.months.shouldContainExactly(listOf(1..12).flatten())
    }
}