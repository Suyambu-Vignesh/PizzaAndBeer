package com.app.pizzaandbeer.ui.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.app.pizzaandbeer.data.error.AppNetworkException
import com.app.pizzaandbeer.data.error.ClientRequestErrorException
import com.app.pizzaandbeer.data.error.EmptyBusinessInformationException
import com.app.pizzaandbeer.data.error.NoLocationPermissionException
import com.app.pizzaandbeer.data.error.ServerErrorException
import com.app.pizzaandbeer.ui.model.ErrorState
import org.junit.Rule
import org.junit.Test

/**
 * Testsuite for [ErrorView]
 */
class ErrorViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testErrorViewForClientRequestErrorException() {
        // Set up the Compose content
        composeTestRule.setContent {
            ErrorView(
                ErrorState(
                    ClientRequestErrorException(404),
                ),
            ) {
            }
        }

        // Verify if the text "Hello, World!" is displayed
        composeTestRule.onNodeWithText("There is some issue, no worries we are on top of the issue.")
            .assertIsDisplayed()
    }

    @Test
    fun testErrorViewForServerException() {
        // Set up the Compose content
        composeTestRule.setContent {
            ErrorView(
                ErrorState(
                    ServerErrorException(500),
                ),
            ) {
            }
        }

        composeTestRule.onNodeWithText("There is some issue, no worries we are on top of the issue.")
            .assertIsDisplayed()
    }

    @Test
    fun testErrorViewForAppNetworkException() {
        // Set up the Compose content
        composeTestRule.setContent {
            ErrorView(
                ErrorState(
                    AppNetworkException(),
                ),
            ) {
            }
        }

        composeTestRule.onNodeWithText("Looks like there is some issue with your network. Please check and give a Retry.")
            .assertIsDisplayed()
    }

    @Test
    fun testErrorViewForEmptyBusinessInformationException() {
        // Set up the Compose content
        composeTestRule.setContent {
            ErrorView(
                ErrorState(
                    EmptyBusinessInformationException(),
                ),
            ) {
            }
        }

        composeTestRule.onNodeWithText("Looks like there is no pizza or bear around you.")
            .assertIsDisplayed()
    }

    @Test
    fun testErrorViewForNoLocationPermissionException() {
        // Set up the Compose content
        composeTestRule.setContent {
            ErrorView(
                ErrorState(
                    NoLocationPermissionException(),
                ),
            ) {
            }
        }

        composeTestRule.onNodeWithText("We need your most valuable location detail to find pizza and beer around you.")
            .assertIsDisplayed()
    }
}
