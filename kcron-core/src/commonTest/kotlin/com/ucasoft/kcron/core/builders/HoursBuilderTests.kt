package com.ucasoft.kcron.core.builders

import com.ucasoft.kcron.core.common.TimeGroups
import io.kotest.matchers.collections.shouldContainExactly
import kotlin.test.BeforeTest
import kotlin.test.Test

class HoursBuilderTests {

    private lateinit var builder: HoursBuilder

    @BeforeTest
    fun setupOnce() {
        builder = HoursBuilder()
    }

    @Test
    fun buildHours() {
        builder.build(TimeGroups.Any, "*")
        builder.hours.shouldContainExactly(listOf(0..23).flatten())
        builder.build(TimeGroups.Specific, "0,5,10,15,20,23")
        builder.hours.shouldContainExactly(0, 5, 10, 15, 20, 23)
        builder.build(TimeGroups.EveryStartingAt, "13/8")
        builder.hours.shouldContainExactly(13, 21)
        builder.build(TimeGroups.EveryBetween, "8-17")
        builder.hours.shouldContainExactly(listOf(8..17).flatten())
        builder.build(TimeGroups.Any, "*")
        builder.hours.shouldContainExactly(listOf(0..23).flatten())
    }
}