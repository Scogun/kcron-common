package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.YearGroup
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class YearsBuilderTests {

    lateinit var builder : YearsBuilder

    private var currentYear : Int = 2020

    @BeforeTest
    fun setupOnce() {
        builder = YearsBuilder()
        currentYear = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
    }

    @Test
    fun buildSeconds() {
        builder.build(YearGroup.Any, "*")
        assertEquals(currentYear, builder.years[0])
        assertEquals(2099, builder.years.last())
        builder.build(YearGroup.Specific, "2020,2045,2060,2079,2099")
        assertEquals(listOf(2020, 2045, 2060, 2079, 2099), builder.years)
        builder.build(YearGroup.EveryStartingAt, "2021/35")
        assertEquals(listOf(2021, 2056, 2091), builder.years)
        builder.build(YearGroup.EveryBetween, "2021-2025")
        assertEquals(listOf(2021, 2022, 2023, 2024, 2025), builder.years)
    }
}