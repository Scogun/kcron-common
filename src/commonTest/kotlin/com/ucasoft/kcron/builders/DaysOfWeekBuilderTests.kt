package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.DayOfWeekGroups
import com.ucasoft.kcron.common.WeekDays
import kotlin.test.Test
import kotlin.test.assertEquals

class DaysOfWeekBuilderTests {

    private lateinit var builder : DaysOfWeekBuilder

    @Test
    fun buildDaysOfWeek() {
        builder = DaysOfWeekBuilder()
        builder.build(DayOfWeekGroups.Any, "*")
        assertEquals(listOf(1..7).flatten(), builder.daysOfWeek)
        builder.build(DayOfWeekGroups.Specific, "MON,TUE,THU,FRI,SUN")
        assertEquals(listOf(1, 2, 4, 5, 7), builder.daysOfWeek)
        builder.build(DayOfWeekGroups.Specific, "1,3,5,7")
        assertEquals(listOf(1, 3, 5, 7), builder.daysOfWeek)
        builder.build(DayOfWeekGroups.EveryStartingAt, "2/2")
        assertEquals(listOf(2, 4, 6), builder.daysOfWeek)
        builder.build(DayOfWeekGroups.EveryStartingAt, "MON/3")
        assertEquals(listOf(1, 4, 7), builder.daysOfWeek)
        builder.build(DayOfWeekGroups.Last, "2L")
        assertEquals(listOf(-2), builder.daysOfWeek)
        builder.build(DayOfWeekGroups.OfMonth, "1#5")
        assertEquals(listOf(10, 50), builder.daysOfWeek)
        builder.build(DayOfWeekGroups.OfMonth, "SAT#5")
        assertEquals(listOf(60, 50), builder.daysOfWeek)
    }

    @Test
    fun firstDaySunday() {
        builder = DaysOfWeekBuilder(WeekDays.Sunday)
        builder.build(DayOfWeekGroups.Specific, "TUE,THU,FRI,SUN")
        assertEquals(listOf(1, 3, 5, 6), builder.daysOfWeek)
    }

    @Test
    fun firstDayWednesday() {
        builder = DaysOfWeekBuilder(WeekDays.Wednesday)
        builder.build(DayOfWeekGroups.Specific, "MON,TUE,THU,FRI")
        assertEquals(listOf(2, 3, 6, 7), builder.daysOfWeek)
    }
}