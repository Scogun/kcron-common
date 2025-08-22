@file:OptIn(ExperimentalTestApi::class)

package com.ucasoft.kcron.ui.builder

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test

internal class CronUIBuilderTests {

    @Test
    fun initialExpressionTest() = runComposeUiTest {
        setContent {
            CronUiBuilder("0 9 29 10 *")
        }

        onNodeWithText("0 9 29 10 *").assertExists()
    }
}