package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.DayOfWeekGroups
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DaysOfWeekBuilderTests {

    private lateinit var builder : DaysOfWeekBuilder

    @BeforeTest
    fun setupOnce() {
        builder = DaysOfWeekBuilder()
    }

    @Test
    fun buildDaysOfWeek() {
        builder.build(DayOfWeekGroups.Any, "*")
        assertEquals(listOf(1..7).flatten(), builder.daysOfWeek)
        builder.build(DayOfWeekGroups.Specific, "MON,TUE,THU,FRI")
        assertEquals(listOf(2, 3, 5, 6), builder.daysOfWeek)
        builder.build(DayOfWeekGroups.Specific, "1,3,5,7")
        assertEquals(listOf(1, 3, 5, 7), builder.daysOfWeek)
        builder.build(DayOfWeekGroups.EveryStartingAt, "2/2")
        assertEquals(listOf(2, 4, 6), builder.daysOfWeek)
        builder.build(DayOfWeekGroups.Last, "2L")
        assertEquals(listOf(-2), builder.daysOfWeek)
        builder.build(DayOfWeekGroups.OfMonth, "1#5")
        assertEquals(listOf(10, 50), builder.daysOfWeek)
    }
}