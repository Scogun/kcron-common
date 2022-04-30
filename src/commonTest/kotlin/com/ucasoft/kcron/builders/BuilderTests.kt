package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.DayOfWeekGroups
import com.ucasoft.kcron.common.WeekDays
import com.ucasoft.kcron.extensions.*
import kotlinx.datetime.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class BuilderTests {

    @Test
    fun buildMany() {
        val builder = Builder()
        assertEquals("* * * ? * * *", builder.expression)
        builder.days(1)
        assertEquals("* * * 1 * ? *", builder.expression)
        builder.anyDays().years(2099)
        assertEquals("* * * ? * * 2099", builder.expression)
        val expected = LocalDateTime(2099, 1, 1, 0, 0)
        assertEquals(expected, builder.nextRun)
        builder.hours(12).minutes(0).seconds(0)
        var result = builder.nextRunList(2)
        assertEquals(expected.plusHours(12), result[0])
        assertEquals(expected.plusHours(36), result[1])
        builder.anyHours().anyMonths().anySeconds().lastDay()
        assertEquals(expected.plusDays(30), builder.nextRun)
        builder.months(2).lastWorkDay()
        assertEquals(expected.plusDays(57), builder.nextRun)
        builder.daysOfWeek(DayOfWeekGroups.EveryStartingAt, "6/1")
        result = builder.nextRunList(1)
        assertEquals(DayOfWeek.SUNDAY, result[0].dayOfWeek)
        assertEquals(expected.plusDays(31), result[0])
        builder.hours(0).minutes(0).seconds(0).daysOfWeek(2, 4, 6)
        result = builder.nextRunList(3)
        assertEquals(expected.plusDays(33), result[0])
        assertEquals(expected.plusDays(35), result[1])
        assertEquals(expected.plusDays(37), result[2])
        builder.nearestWorkDay(30)
        assertNull(builder.nextRun)
        builder.months(5).nearestWorkDay(31)
        assertEquals(expected.plusDays(148), builder.nextRun)
        builder.months(10)
        assertEquals(expected.plusDays(302), builder.nextRun)
        assertEquals("0 0 0 31W 10 ? 2099", builder.expression)
        builder.seconds(0..50)
        assertEquals("0-50 0 0 31W 10 ? 2099", builder.expression)
        builder.seconds(0).minutes(0..25)
        assertEquals("0 0-25 0 31W 10 ? 2099", builder.expression)
        builder.minutes(0).hours(0..18)
        assertEquals("0 0 0-18 31W 10 ? 2099", builder.expression)
        builder.hours(0).months(1..6)
        assertEquals("0 0 0 31W 1-6 ? 2099", builder.expression)
        assertEquals(expected.plusDays(29), builder.nextRun)
        builder.months(10).seconds(10 at 0)
        assertEquals("0/10 0 0 31W 10 ? 2099", builder.expression)
        builder.seconds(0).months(1).daysOfWeek(1 at 6)
        assertEquals("0 0 0 ? 1 6/1 2099", builder.expression)
        result = builder.nextRunList(2)
        assertEquals(expected.plusDays(2), result[0])
        assertEquals(expected.plusDays(3), result[1])
        builder.daysOfWeek(3 at 6)
        assertEquals("0 0 0 ? 1 6/3 2099", builder.expression)
        result = builder.nextRunList(2)
        assertEquals(expected.plusDays(2), result[0])
        assertEquals(expected.plusDays(9), result[1])
        builder.daysOfWeek(6 on 5)
        assertEquals("0 0 0 ? 1 6#5 2099", builder.expression)
        assertEquals(expected.plusDays(30), builder.nextRun)
    }

    @Test
    fun buildManyFirstWeekDayIsSunday() {
        val builder = Builder(WeekDays.Sunday)
        builder.years(2099).months(2).daysOfWeek(DayOfWeekGroups.EveryStartingAt, "6/1")
        var result = builder.nextRunList(1)
        assertEquals(DayOfWeek.FRIDAY, result[0].dayOfWeek)
        val expected = LocalDateTime(2099, 1, 1, 0, 0)
        assertEquals(expected.plusDays(36), result[0])
        builder.hours(0).minutes(0).seconds(0).daysOfWeek(2, 4, 6)
        result = builder.nextRunList(3)
        assertEquals(expected.plusDays(32), result[0])
        assertEquals(expected.plusDays(34), result[1])
        assertEquals(expected.plusDays(36), result[2])
        builder.seconds(0).months(1).daysOfWeek(1 at 6)
        result = builder.nextRunList(2)
        assertEquals(expected.plusDays(1), result[0])
        assertEquals(expected.plusDays(2), result[1])
        builder.daysOfWeek(3 at 6)
        result = builder.nextRunList(2)
        assertEquals(expected.plusDays(1), result[0])
        assertEquals(expected.plusDays(8), result[1])
        builder.daysOfWeek(7 on 5)
        assertEquals("0 0 0 ? 1 7#5 2099", builder.expression)
        assertEquals(expected.plusDays(30), builder.nextRun)
    }

    @Test
    fun buildManyFirstWeekDayIsWednesday() {
        val builder = Builder(WeekDays.Wednesday)
        builder.years(2099).months(2).daysOfWeek(DayOfWeekGroups.EveryStartingAt, "6/1")
        var result = builder.nextRunList(1)
        assertEquals(DayOfWeek.MONDAY, result[0].dayOfWeek)
        val expected = LocalDateTime(2099, 1, 1, 0, 0)
        assertEquals(expected.plusDays(32), result[0])
        builder.hours(0).minutes(0).seconds(0).daysOfWeek(2, 4, 6)
        result = builder.nextRunList(3)
        assertEquals(expected.plusDays(32), result[0])
        assertEquals(expected.plusDays(35), result[1])
        assertEquals(expected.plusDays(37), result[2])
        builder.seconds(0).months(1).daysOfWeek(1 at 6)
        result = builder.nextRunList(2)
        assertEquals(expected.plusDays(4), result[0])
        assertEquals(expected.plusDays(5), result[1])
        builder.daysOfWeek(3 at 6)
        result = builder.nextRunList(2)
        assertEquals(expected.plusDays(4), result[0])
        assertEquals(expected.plusDays(11), result[1])
        builder.daysOfWeek(2 on 5)
        assertEquals("0 0 0 ? 1 2#5 2099", builder.expression)
        assertEquals(expected.plusDays(28), builder.nextRun)
    }
}
