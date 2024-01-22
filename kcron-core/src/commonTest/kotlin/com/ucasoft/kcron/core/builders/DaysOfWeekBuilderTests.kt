package com.ucasoft.kcron.core.builders

import com.ucasoft.kcron.core.common.DayOfWeekGroups
import com.ucasoft.kcron.core.common.WeekDays
import com.ucasoft.kcron.core.exceptions.UnknownCronPart
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class DaysOfWeekBuilderTests {

    private lateinit var builder : DaysOfWeekBuilder

    @Test
    fun buildDaysOfWeek() {
        builder = DaysOfWeekBuilder()
        builder.build(DayOfWeekGroups.Any, "*")
        builder.daysOfWeek.shouldContainExactly(listOf(1..7).flatten())
        builder.build(DayOfWeekGroups.Specific, "MON,TUE,THU,FRI,SUN")
        builder.daysOfWeek.shouldContainExactly(1, 2, 4, 5 ,7)
        builder.build(DayOfWeekGroups.Specific, "1,3,5,7")
        builder.daysOfWeek.shouldContainExactly(1, 3, 5 ,7)
        builder.build(DayOfWeekGroups.EveryStartingAt, "2/2")
        builder.daysOfWeek.shouldContainExactly(2, 4, 6)
        builder.build(DayOfWeekGroups.EveryStartingAt, "MON/3")
        builder.daysOfWeek.shouldContainExactly(1, 4, 7)
        builder.build(DayOfWeekGroups.Last, "2L")
        builder.daysOfWeek.shouldBeSingleton {
            it.shouldBe(-2)
        }
        builder.build(DayOfWeekGroups.OfMonth, "1#5")
        builder.daysOfWeek.shouldContainExactly(10, 50)
        builder.build(DayOfWeekGroups.OfMonth, "SAT#5")
        builder.daysOfWeek.shouldContainExactly(60, 50)
        shouldThrow<UnknownCronPart> {
            builder.build(DayOfWeekGroups.Unknown, "")
        }
    }

    @Test
    fun firstDaySunday() {
        builder = DaysOfWeekBuilder(WeekDays.Sunday)
        builder.build(DayOfWeekGroups.Specific, "TUE,THU,FRI,SUN")
        builder.daysOfWeek.shouldContainExactly(1, 3, 5, 6)
    }

    @Test
    fun firstDayWednesday() {
        builder = DaysOfWeekBuilder(WeekDays.Wednesday)
        builder.build(DayOfWeekGroups.Specific, "MON,TUE,THU,FRI")
        builder.daysOfWeek.shouldContainExactly(2, 3, 6, 7)
    }
}