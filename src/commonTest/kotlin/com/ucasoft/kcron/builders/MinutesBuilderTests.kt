package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.TimeGroups
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MinutesBuilderTests {

    private lateinit var builder: MinutesBuilder

    @BeforeTest
    fun setupOnce() {
        builder = MinutesBuilder()
    }

    @Test
    fun buildSeconds() {
        builder.build(TimeGroups.Any, "*")
        assertEquals(listOf(0..59).flatten(), builder.minutes)
        builder.build(TimeGroups.Specific, "0,5,10,15,20,30,45,59")
        assertEquals(listOf(0, 5, 10, 15, 20, 30, 45, 59), builder.minutes)
        builder.build(TimeGroups.EveryStartingAt, "13/17")
        assertEquals(listOf(13, 30, 47), builder.minutes)
        builder.build(TimeGroups.EveryBetween, "29-37")
        assertEquals(listOf(29..37).flatten(), builder.minutes)
        builder.build(TimeGroups.Any, "*")
        assertEquals(listOf(0..59).flatten(), builder.minutes)
    }
}