package com.ucasoft.kcron

import com.ucasoft.kcron.exceptions.WrongCronExpression
import com.ucasoft.kcron.settings.Version
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test

class BuildAndParseTests {

    private var currentYear : Int = 2023
    
    @BeforeTest
    fun setupOnce() {
        currentYear = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
    }
    
    @Test
    fun parseAndBuildAuto() {
        var builder = KCron.parseAndBuild("* 12 ? * *")
        builder.expression.shouldBe("0 * 12 ? * * *")
        builder.nextRun.shouldNotBeNull()
            .year.shouldBe(currentYear)
        builder = KCron.parseAndBuild("30 * * ? * * 2050")
        builder.expression.shouldBe("30 * * ? * * 2050")
        builder.nextRun.shouldNotBeNull()
            .year.shouldBe(2050)
        shouldThrowWithMessage<WrongCronExpression>("Expression * * * ? * * is not Cron one!") {
            KCron.parseAndBuild("* * * ? * *")
        }
    }
    
    @Test
    fun parseAndBuildClassic() {
        val builder = KCron.parseAndBuild("* 12 ? * *") {
            it.version = Version.Classic
        }
        builder.expression.shouldBe("0 * 12 ? * * *")
        builder.nextRun.shouldNotBeNull()
            .year.shouldBe(currentYear)
        shouldThrowWithMessage<WrongCronExpression>("Expression * * * ? * * * is not Classic Cron one!") {
            KCron.parseAndBuild("* * * ? * * *") {
                it.version = Version.Classic
            }
        }
    }
    
    @Test
    fun parseAndBuildModern() {
        val builder = KCron.parseAndBuild("30 * * ? * * 2050") {
            it.version = Version.Modern
        }
        builder.expression.shouldBe("30 * * ? * * 2050")
        builder.nextRun.shouldNotBeNull()
            .year.shouldBe(2050)
        shouldThrowWithMessage<WrongCronExpression>("Expression * * ? * * is not Modern Cron one!") {
            KCron.parseAndBuild("* * ? * *") {
                it.version = Version.Modern
            }
        }
    }
    
}