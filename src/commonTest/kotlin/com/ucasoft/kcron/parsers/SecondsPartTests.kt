package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.CronPart
import com.ucasoft.kcron.common.TimeGroups
import io.kotest.assertions.withClue
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlin.test.*

class SecondsPartTests {

    private lateinit var parser: SecondsMinutesParser

    private val allowedValues = listOf(
        TimeValueGroup("*", TimeGroups.Any),
        TimeValueGroup("0", TimeGroups.Specific),
        TimeValueGroup("15", TimeGroups.Specific),
        TimeValueGroup("59", TimeGroups.Specific),
        TimeValueGroup("0,59", TimeGroups.Specific),
        TimeValueGroup("0,10,15,35,55", TimeGroups.Specific),
        TimeValueGroup("0-59", TimeGroups.EveryBetween),
        TimeValueGroup("5-15", TimeGroups.EveryBetween),
        TimeValueGroup("10-45", TimeGroups.EveryBetween),
        TimeValueGroup("0/59", TimeGroups.EveryStartingAt),
        TimeValueGroup("10/45", TimeGroups.EveryStartingAt),
        TimeValueGroup("59/59", TimeGroups.EveryStartingAt),
        TimeValueGroup("59/60",  TimeGroups.EveryStartingAt)
    )

    private val deniedValues = listOf(
        "**",
        "60",
        "15,",
        ",59",
        "0,60",
        "0,10,15,35,55,72",
        "0-",
        "-15",
        "10-60",
        "60/59",
        "59/61"
    )

    @BeforeTest
    fun setupOnce() {
        parser = SecondsMinutesParser(CronPart.Seconds)
    }

    @Test
    fun allowedValues() {
        for (value in allowedValues) {
            parser.parse(value.value)
            parser.parse(value.value)
            withClue(value.value) {
                parser.isValid.shouldBeTrue()
            }
            parser.group.shouldBe(value.group)
        }
    }

    @Test
    fun deniedValues() {
        for (value in deniedValues) {
            parser.parse(value)
            parser.isValid.shouldBeFalse()
            parser.group.shouldBe(TimeGroups.Unknown)
        }
    }
}