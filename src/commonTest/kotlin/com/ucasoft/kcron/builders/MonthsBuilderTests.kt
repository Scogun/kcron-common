package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.MonthGroups
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MonthsBuilderTests {

    private lateinit var builder: MonthsBuilder

    @BeforeTest
    fun setupOnce() {
        builder = MonthsBuilder()
    }

    @Test
    fun buildMonths() {
        builder.build(MonthGroups.Any, "*")
        assertEquals(listOf(1..12).flatten(), builder.months)
        builder.build(MonthGroups.Specific, "1,2,6,10,12")
        assertEquals(listOf(1, 2, 6, 10, 12), builder.months)
        builder.build(MonthGroups.Specific, "JAN,FEB,JUN,OCT,DEC")
        assertEquals(listOf(1, 2, 6, 10, 12), builder.months)
        builder.build(MonthGroups.EveryStartingAt, "3/2")
        assertEquals(listOf(3, 5, 7, 9, 11), builder.months)
        builder.build(MonthGroups.EveryBetween, "6-8")
        assertEquals(listOf(6, 7, 8), builder.months)
        builder.build(MonthGroups.Any, "*")
        assertEquals(listOf(1..12).flatten(), builder.months)
    }
}