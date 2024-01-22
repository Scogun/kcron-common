package com.ucasoft.kcron.core.builders

import com.ucasoft.kcron.abstractions.CronDateTimeProvider
import com.ucasoft.kcron.core.common.YearGroups
import com.ucasoft.kcron.kotlinx.datetime.CronLocalDateTimeProvider
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldEndWith
import io.kotest.matchers.collections.shouldHaveElementAt
import kotlin.test.BeforeTest
import kotlin.test.Test

class YearsBuilderTests {

    private val dateTimeProvider = CronLocalDateTimeProvider()

    private lateinit var builder : YearsBuilder<CronDateTimeProvider<*, *>>

    private var currentYear : Int = 2020

    @BeforeTest
    fun setupOnce() {
        builder = YearsBuilder(dateTimeProvider)
        currentYear = dateTimeProvider.now().year
    }

    @Test
    fun buildYears() {
        builder.build(YearGroups.Any, "*")
        builder.years.shouldHaveElementAt(0, currentYear)
        builder.years.shouldEndWith(2099)
        builder.build(YearGroups.Specific, "2020,2045,2060,2079,2099")
        builder.years.shouldContainExactly(2020, 2045, 2060, 2079, 2099)
        builder.build(YearGroups.EveryStartingAt, "2021/35")
        builder.years.shouldContainExactly(2021, 2056, 2091)
        builder.build(YearGroups.EveryBetween, "2021-2025")
        builder.years.shouldContainExactly(2021, 2022, 2023, 2024, 2025)
        builder.build(YearGroups.Any, "*")
        builder.years.shouldHaveElementAt(0, currentYear)
        builder.years.shouldEndWith(2099)
    }
}