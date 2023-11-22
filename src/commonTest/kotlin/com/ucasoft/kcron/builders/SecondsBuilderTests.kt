package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.TimeGroups
import io.kotest.matchers.collections.shouldContainExactly
import kotlin.test.BeforeTest
import kotlin.test.Test

class SecondsBuilderTests {

    private lateinit var builder: SecondsBuilder

    @BeforeTest
    fun setupOnce() {
        builder = SecondsBuilder()
    }

    @Test
    fun buildSeconds() {
        builder.build(TimeGroups.Any, "*")
        builder.seconds.shouldContainExactly(listOf(0..59).flatten())
        builder.build(TimeGroups.Specific, "0,5,10,15,20,30,45,59")
        builder.seconds.shouldContainExactly(0, 5, 10, 15, 20, 30, 45, 59)
        builder.build(TimeGroups.EveryStartingAt, "13/17")
        builder.seconds.shouldContainExactly(13, 30, 47)
        builder.build(TimeGroups.EveryBetween, "29-37")
        builder.seconds.shouldContainExactly(listOf(29..37).flatten())
        builder.build(TimeGroups.Any, "*")
        builder.seconds.shouldContainExactly(listOf(0..59).flatten())
    }
}