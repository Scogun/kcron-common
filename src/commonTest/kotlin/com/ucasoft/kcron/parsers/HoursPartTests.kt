package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.TimeGroups
import kotlin.test.*

class HoursPartTests {

    private lateinit var parser: HoursParser

    private val allowedValues = listOf(
        TimeValueGroup("*", TimeGroups.Any),
        TimeValueGroup("0", TimeGroups.Specific),
        TimeValueGroup("15", TimeGroups.Specific),
        TimeValueGroup("23", TimeGroups.Specific),
        TimeValueGroup("0,23", TimeGroups.Specific),
        TimeValueGroup("0,10,15,23", TimeGroups.Specific),
        TimeValueGroup("0-23", TimeGroups.EveryBetween),
        TimeValueGroup("5-15", TimeGroups.EveryBetween),
        TimeValueGroup("10-20", TimeGroups.EveryBetween),
        TimeValueGroup("0/23", TimeGroups.EveryStartingAt),
        TimeValueGroup("10/20", TimeGroups.EveryStartingAt),
        TimeValueGroup("23/23", TimeGroups.EveryStartingAt),
        TimeValueGroup("23/24",  TimeGroups.EveryStartingAt)
    )

    private val deniedValues = listOf(
        "**",
        "24",
        "15,",
        ",23",
        "0,24",
        "0,10,15,30",
        "0-",
        "-15",
        "10-24",
        "24/23",
        "23/25"
    )

    @BeforeTest
    fun setupOnce() {
        parser = HoursParser()
    }

    @Test
    fun allowedValues() {
        for (value in allowedValues) {
            parser.parse(value.value)
            assertTrue(parser.isValid)
            assertEquals(value.group, parser.group)
        }
    }

    @Test
    fun deniedValues() {
        for (value in deniedValues) {
            parser.parse(value)
            assertFalse(parser.isValid)
            assertEquals(TimeGroups.Unknown, parser.group)
        }
    }
}