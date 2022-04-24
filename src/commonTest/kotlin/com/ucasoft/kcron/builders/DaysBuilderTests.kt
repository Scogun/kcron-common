package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.DayGroups
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DaysBuilderTests {

    private lateinit var builder: DaysBuilder

    @BeforeTest
    fun setupOnce() {
        builder = DaysBuilder()
    }

    @Test
    fun buildSeconds() {
        builder.build(DayGroups.Any, "?")
        assertEquals(listOf(1..31).flatten(), builder.days)
        builder.build(DayGroups.Specific, "1,5,10,15,20,23,31")
        assertEquals(listOf(1, 5, 10, 15, 20, 23, 31), builder.days)
        builder.build(DayGroups.EveryStartingAt, "13/8")
        assertEquals(listOf(13, 21, 29), builder.days)
        builder.build(DayGroups.LastDay, "L")
        assertEquals(listOf(-32), builder.days)
        builder.build(DayGroups.LastWeekday, "LW")
        assertEquals(listOf(32), builder.days)
        builder.build(DayGroups.BeforeTheEnd, "L-3")
        assertEquals(listOf(-3), builder.days)
        builder.build(DayGroups.NearestWeekday, "25W")
        assertEquals(listOf(2500), builder.days)
    }
}