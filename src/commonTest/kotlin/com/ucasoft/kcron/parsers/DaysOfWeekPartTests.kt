package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.DayOfWeekGroups
import kotlin.test.*

class DaysOfWeekPartTests {

    private lateinit var parser: DaysOfWeekParser

    private val allowedValues = listOf(
        DayOfWeekValueGroup("*", DayOfWeekGroups.Any),
        DayOfWeekValueGroup("?", DayOfWeekGroups.Any),
        DayOfWeekValueGroup("1", DayOfWeekGroups.Specific),
        DayOfWeekValueGroup("3", DayOfWeekGroups.Specific),
        DayOfWeekValueGroup("7", DayOfWeekGroups.Specific),
        DayOfWeekValueGroup("1,7", DayOfWeekGroups.Specific),
        DayOfWeekValueGroup("1,3,5,7", DayOfWeekGroups.Specific),
        DayOfWeekValueGroup("1/1", DayOfWeekGroups.EveryStartingAt),
        DayOfWeekValueGroup("1/7", DayOfWeekGroups.EveryStartingAt),
        DayOfWeekValueGroup("7/7", DayOfWeekGroups.EveryStartingAt),
        DayOfWeekValueGroup("7/1", DayOfWeekGroups.EveryStartingAt),
        DayOfWeekValueGroup("1L", DayOfWeekGroups.Last),
        DayOfWeekValueGroup("7L", DayOfWeekGroups.Last),
        DayOfWeekValueGroup("7#1", DayOfWeekGroups.OfMonth),
        DayOfWeekValueGroup("1#5", DayOfWeekGroups.OfMonth),
        DayOfWeekValueGroup("6#2", DayOfWeekGroups.OfMonth)
    )

    private val deniedValues = listOf(
        "**",
        "??",
        "?*",
        "0",
        "8",
        "4,",
        ",7",
        "0,7",
        "1,3,5,8",
        "1/",
        "1/8",
        "/7",
        "8/2",
        "0L",
        "8L",
        "6M",
        "8#1",
        "6#6"
    )

    @BeforeTest
    fun setupOnce() {
        parser = DaysOfWeekParser()
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
            assertEquals(DayOfWeekGroups.Unknown, parser.group)
        }
    }
}