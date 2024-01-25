package com.ucasoft.kcron.core.builders

import com.ucasoft.kcron.core.common.DayGroups
import com.ucasoft.kcron.core.exceptions.UnknownCronPart
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import kotlin.test.BeforeTest
import kotlin.test.Test

class DaysBuilderTests {

    private lateinit var builder: DaysBuilder

    @BeforeTest
    fun setupOnce() {
        builder = DaysBuilder()
    }

    @Test
    fun buildDays() {
        builder.build(DayGroups.Any, "?")
        builder.days.shouldContainExactly(listOf(1..31).flatten())
        builder.build(DayGroups.Specific, "1,5,10,15,20,23,31")
        builder.days.shouldContainExactly(1, 5, 10, 15, 20, 23, 31)
        builder.build(DayGroups.EveryStartingAt, "13/8")
        builder.days.shouldContainExactly(13, 21, 29)
        builder.build(DayGroups.LastDay, "L")
        builder.days.shouldBeSingleton {
            it.shouldBe(-32)
        }
        builder.build(DayGroups.LastWeekday, "LW")
        builder.days.shouldBeSingleton {
            it.shouldBe(32)
        }
        builder.build(DayGroups.BeforeTheEnd, "L-3")
        builder.days.shouldBeSingleton {
            it.shouldBe(-3)
        }
        builder.build(DayGroups.NearestWeekday, "25W")
        builder.days.shouldBeSingleton {
            it.shouldBe(2500)
        }
        shouldThrow<UnknownCronPart> {
            builder.build(DayGroups.Unknown, "")
        }
    }
}