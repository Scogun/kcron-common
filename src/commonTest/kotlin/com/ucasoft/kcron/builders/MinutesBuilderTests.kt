package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.TimeGroups
import io.kotest.matchers.collections.shouldContainExactly
import kotlin.test.BeforeTest
import kotlin.test.Test

class MinutesBuilderTests {

    private lateinit var builder: MinutesBuilder

    @BeforeTest
    fun setupOnce() {
        builder = MinutesBuilder()
    }

    @Test
    fun buildMinutes() {
        builder.build(TimeGroups.Any, "*")
        builder.minutes.shouldContainExactly(listOf(0..59).flatten())
        builder.build(TimeGroups.Specific, "0,5,10,15,20,30,45,59")
        builder.minutes.shouldContainExactly(0, 5, 10, 15, 20, 30, 45, 59)
        builder.build(TimeGroups.EveryStartingAt, "13/17")
        builder.minutes.shouldContainExactly(13, 30, 47)
        builder.build(TimeGroups.EveryBetween, "29-37")
        builder.minutes.shouldContainExactly(listOf(29..37).flatten())
        builder.build(TimeGroups.Any, "*")
        builder.minutes.shouldContainExactly(listOf(0..59).flatten())
    }
}