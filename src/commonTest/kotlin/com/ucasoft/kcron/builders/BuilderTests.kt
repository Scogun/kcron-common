package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.DayOfWeekGroups
import com.ucasoft.kcron.common.WeekDays
import com.ucasoft.kcron.extensions.*
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.collections.shouldHaveElementAt
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.datetime.*
import kotlin.test.Test

@OptIn(DelicateIterableApi::class)
class BuilderTests {

    @Test
    fun buildMany() {
        val builder = Builder()
        builder.expression.shouldBe("* * * ? * * *")
        builder.days(1)
        builder.expression.shouldBe("* * * 1 * ? *")
        builder.anyDays().years(2099)
        builder.expression.shouldBe("* * * ? * * 2099")
        val expected = LocalDateTime(2099, 1, 1, 0, 0)
        builder.nextRun.shouldBe(expected)
        builder.hours(12).minutes(0).seconds(0)
        var result = builder.asIterable().take(2)
        result.shouldHaveElementAt(0, expected.plusHours(12))
        result.shouldHaveElementAt(1, expected.plusHours(36))
        builder.anyHours().anyMonths().anySeconds().lastDay()
        builder.nextRun.shouldBe(expected.plusDays(30))
        builder.months(2).lastWorkDay()
        builder.nextRun.shouldBe(expected.plusDays(57))
        builder.daysOfWeek(DayOfWeekGroups.EveryStartingAt, "6/1")
        result = builder.asIterable().take(1)
        result.shouldBeSingleton {
            it.dayOfWeek.shouldBe(DayOfWeek.SUNDAY)
            it.shouldBe(expected.plusDays(31))
        }
        builder.hours(0).minutes(0).seconds(0).daysOfWeek(2, 4, 6)
        result = builder.asIterable().take(3)
        result.shouldHaveElementAt(0, expected.plusDays(33))
        result.shouldHaveElementAt(1, expected.plusDays(35))
        result.shouldHaveElementAt(2, expected.plusDays(37))
        builder.nearestWorkDay(30)
        builder.nextRun.shouldBeNull()
        builder.months(5).nearestWorkDay(31)
        builder.nextRun.shouldBe(expected.plusDays(148))
        builder.months(10)
        builder.nextRun.shouldBe(expected.plusDays(302))
        builder.expression.shouldBe("0 0 0 31W 10 ? 2099")
        builder.seconds(0..50)
        builder.expression.shouldBe("0-50 0 0 31W 10 ? 2099")
        builder.seconds(0).minutes(0..25)
        builder.expression.shouldBe("0 0-25 0 31W 10 ? 2099")
        builder.minutes(0).hours(0..18)
        builder.expression.shouldBe("0 0 0-18 31W 10 ? 2099")
        builder.hours(0).months(1..6)
        builder.expression.shouldBe("0 0 0 31W 1-6 ? 2099")
        builder.nextRun.shouldBe(expected.plusDays(29))
        builder.months(10).seconds(10 at 0)
        builder.expression.shouldBe("0/10 0 0 31W 10 ? 2099")
        builder.seconds(0).months(1).daysOfWeek(1 at 6)
        builder.expression.shouldBe("0 0 0 ? 1 6/1 2099")
        result = builder.asIterable().take(2)
        result.shouldHaveElementAt(0, expected.plusDays(2))
        result.shouldHaveElementAt(1, expected.plusDays(3))
        builder.daysOfWeek(3 at WeekDays.Saturday)
        builder.expression.shouldBe("0 0 0 ? 1 SAT/3 2099")
        result = builder.asIterable().take(2)
        result.shouldHaveElementAt(0, expected.plusDays(2))
        result.shouldHaveElementAt(1, expected.plusDays(9))
        builder.daysOfWeek(6 on 5)
        builder.expression.shouldBe("0 0 0 ? 1 6#5 2099")
        builder.nextRun.shouldBe(expected.plusDays(30))
    }

    @Test
    fun buildManyFirstWeekDayIsSunday() {
        val expected = LocalDateTime(2099, 1, 1, 0, 0)
        val builder = Builder(WeekDays.Sunday)
        builder.years(2099).months(2).daysOfWeek(DayOfWeekGroups.EveryStartingAt, "6/1")
        var result = builder.asIterable().take(1)
        result.shouldBeSingleton {
            it.dayOfWeek.shouldBe(DayOfWeek.FRIDAY)
            it.shouldBe(expected.plusDays(36))
        }
        builder.hours(0).minutes(0).seconds(0).daysOfWeek(2, 4, 6)
        result = builder.asIterable().take(3)
        result.shouldHaveElementAt(0, expected.plusDays(32))
        result.shouldHaveElementAt(1, expected.plusDays(34))
        result.shouldHaveElementAt(2, expected.plusDays(36))
        builder.seconds(0).months(1).daysOfWeek(1 at 6)
        result = builder.asIterable().take(2)
        result.shouldHaveElementAt(0, expected.plusDays(1))
        result.shouldHaveElementAt(1, expected.plusDays(2))
        builder.daysOfWeek(3 at WeekDays.Friday)
        result = builder.asIterable().take(2)
        result.shouldHaveElementAt(0, expected.plusDays(1))
        result.shouldHaveElementAt(1, expected.plusDays(8))
        builder.daysOfWeek(WeekDays.Saturday on 5)
        builder.expression.shouldBe("0 0 0 ? 1 SAT#5 2099")
        builder.nextRun.shouldBe(expected.plusDays(30))
    }

    @Test
    fun buildManyFirstWeekDayIsWednesday() {
        val expected = LocalDateTime(2099, 1, 1, 0, 0)
        val builder = Builder(WeekDays.Wednesday)
        builder.years(2099).months(2).daysOfWeek(DayOfWeekGroups.EveryStartingAt, "6/1")
        var result = builder.asIterable().take(1)
        result.shouldBeSingleton {
            it.dayOfWeek.shouldBe(DayOfWeek.MONDAY)
            it.shouldBe(expected.plusDays(32))
        }
        builder.hours(0).minutes(0).seconds(0).daysOfWeek(2, 4, 6)
        result = builder.asIterable().take(3)
        result.shouldHaveElementAt(0, expected.plusDays(32))
        result.shouldHaveElementAt(1, expected.plusDays(35))
        result.shouldHaveElementAt(2, expected.plusDays(37))
        builder.seconds(0).months(1).daysOfWeek(1 at 6)
        result = builder.asIterable().take(2)
        result.shouldHaveElementAt(0, expected.plusDays(4))
        result.shouldHaveElementAt(1, expected.plusDays(5))
        builder.daysOfWeek(3 at WeekDays.Monday)
        result = builder.asIterable().take(2)
        result.shouldHaveElementAt(0, expected.plusDays(4))
        result.shouldHaveElementAt(1, expected.plusDays(11))
        builder.daysOfWeek(WeekDays.Thursday on 5)
        builder.expression.shouldBe("0 0 0 ? 1 THU#5 2099")
        builder.nextRun.shouldBe(expected.plusDays(28))
    }

    @Test
    fun iterableShouldStartFromStartDateTimeWhenItInSpecifiedRange() {
        // setup cron to run every day at 12:00:00 from 2050 to 2070
        val builder = Builder().years(2050..2070).hours(12).minutes(0).seconds(0)
        // set start datetime for iterable to 1 Jan 2060 00:00:00
        val start = LocalDate(2060, 1, 1).atTime(0, 0, 0)
        // expect first run in 1 Jan 2060 12:00:00
        val expected = LocalDate(2060, 1, 1).atTime(12, 0, 0)

        val actual = builder.asIterable(start).first()
        actual.shouldBeEqual(expected)
    }

    @Test
    fun iterableShouldStartFromFirstDatetimeDefinedByCronWhenStartDateTimeIsBeforeSpecifiedRange() {
        // setup cron to run every day at 12:00:00 from 2050 to 2070
        val builder = Builder().years(2050..2070).hours(12).minutes(0).seconds(0)
        // set start datetime for iterable to 1 Jan 2010 00:00:00
        val start = LocalDate(2010, 1, 1).atTime(0, 0, 0)
        // expect first run in 1 Jan 2050 12:00:00 (as defined in cron)
        val expected = LocalDate(2050, 1, 1).atTime(12, 0, 0)

        val actual = builder.asIterable(start).first()
        actual.shouldBeEqual(expected)
    }

    @Test
    fun iterableShouldBeEmptyWhenStartDateTimeIsAfterSpecifiedRange() {
        // setup cron to run every day at 12:00:00 from 2050 to 2070
        val builder = Builder().years(2050..2070).hours(12).minutes(0).seconds(0)
        // set start datetime for iterable to 1 Jan 2080 00:00:00
        val start = LocalDate(2080, 1, 1).atTime(0, 0, 0)

        val actual = builder.asIterable(start).take(2)
        // expect actual value to be empty because 2080 is out of 2050..2070 range
        actual.shouldBeEmpty()
    }

    @Test
    fun iterableShouldBeFiniteForCronWithSpecifiedRange() {
        // setup cron to run every day at 12:00:00 from 2050 to 2070
        val builder = Builder().years(2050..2070).hours(12).minutes(0).seconds(0)
        // set start datetime for iterable to 1 Jan 2070 00:00:00
        val start = LocalDate(2070, 1, 1).atTime(0, 0, 0)
        // set expected runs count to be exactly 365 (once per day)
        val expected = 365

        val actual = builder.asIterable(start).toList()
        actual.shouldHaveSize(expected)
    }
}
