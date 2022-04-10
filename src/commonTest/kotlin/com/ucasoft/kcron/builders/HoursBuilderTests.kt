package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.TimeGroups
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class HoursBuilderTests {

    private lateinit var builder: HoursBuilder

    @BeforeTest
    fun setupOnce() {
        builder = HoursBuilder()
    }

    @Test
    fun buildSeconds() {
        builder.build(TimeGroups.Any, "*")
        assertEquals(listOf(0..23).flatten(), builder.hours)
        builder.build(TimeGroups.Specific, "0,5,10,15,20,23")
        assertEquals(listOf(0, 5, 10, 15, 20, 23), builder.hours)
        builder.build(TimeGroups.EveryStartingAt, "13/8")
        assertEquals(listOf(13, 21), builder.hours)
        builder.build(TimeGroups.EveryBetween, "8-17")
        assertEquals(listOf(8..17).flatten(), builder.hours)
    }
}