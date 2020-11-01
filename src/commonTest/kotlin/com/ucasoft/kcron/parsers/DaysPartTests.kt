package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.DayGroups
import kotlin.test.*

class DaysPartTests {

    private lateinit var parser: DaysParser

    private val allowedValues = listOf(
        DayValueGroup("?", DayGroups.Any),
        DayValueGroup("1", DayGroups.Specific),
        DayValueGroup("15", DayGroups.Specific),
        DayValueGroup("31", DayGroups.Specific),
        DayValueGroup("1,31", DayGroups.Specific),
        DayValueGroup("1,10,15,31", DayGroups.Specific),
        DayValueGroup("1/31", DayGroups.EveryStartingAt),
        DayValueGroup("5/15", DayGroups.EveryStartingAt),
        DayValueGroup("10/20", DayGroups.EveryStartingAt),
        DayValueGroup("L", DayGroups.LastDay),
        DayValueGroup("LW", DayGroups.LastWeekday),
        DayValueGroup("L-1", DayGroups.BeforeTheEnd),
        DayValueGroup("L-10", DayGroups.BeforeTheEnd),
        DayValueGroup("L-31", DayGroups.BeforeTheEnd),
        DayValueGroup("1W", DayGroups.NearestWeekday),
        DayValueGroup("15W", DayGroups.NearestWeekday),
        DayValueGroup("31W", DayGroups.NearestWeekday)
    )

    val deniedValues = listOf(
        "*",
        "??",
        "0",
        "32",
        "15,",
        ",23",
        "0,31",
        "1,10,15,32",
        "1/",
        "/15",
        "10/32",
        "M",
        "N",
        "LV",
        "LL",
        "L-0",
        "L-32",
        "0W",
        "32W"
    )

    @BeforeTest
    fun setupOnce() {
        parser = DaysParser()
    }

    @Test
    fun allowedValues() {
        for (value in allowedValues) {
            parser.parse(value.value)
            assertTrue(parser.isValid, value.value)
            assertEquals(value.group, parser.group)
        }
    }

    @Test
    fun deniedValues() {
        for (value in deniedValues) {
            parser.parse(value)
            assertFalse(parser.isValid)
            assertEquals(DayGroups.Unknown, parser.group)
        }
    }
}