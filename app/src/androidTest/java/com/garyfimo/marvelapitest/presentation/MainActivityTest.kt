package com.garyfimo.marvelapitest.presentation

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.garyfimo.marvelapitest.data.repository.MarvelRepositoryFakeImpl
import com.garyfimo.marvelapitest.domain.character.MarvelRepository
import com.garyfimo.marvelapitest.presentation.common.AppBarIcon
import com.garyfimo.marvelapitest.presentation.list.LIST_TEST_TAG
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var marvelRepository: MarvelRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_error_screen() {
        (marvelRepository as MarvelRepositoryFakeImpl).setShouldResponseError(true)
        composeTestRule.onNodeWithText(ERROR_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithText(ERROR_TEXT).assertHasNoClickAction()
        composeTestRule.onNodeWithText(ERROR_RETRY_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithText(ERROR_RETRY_TEXT).assertHasClickAction()
        composeTestRule.onNodeWithContentDescription(THANOS_FACE_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(THANOS_FACE_TEXT).assertHasNoClickAction()
    }

    @Test
    fun test_loading_screen() {
        (marvelRepository as MarvelRepositoryFakeImpl).setShouldAwait(true)
        composeTestRule.onNodeWithContentDescription(AVENGERS_LOGO_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(AVENGERS_LOGO_TEXT).assertHasNoClickAction()
        composeTestRule.onNodeWithText(LOADING_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithText(LOADING_TEXT).assertHasNoClickAction()
    }

    @Test
    fun test_success_drawer() {
        composeTestRule.onNodeWithTag(AppBarIcon).performClick()
        composeTestRule.onNodeWithContentDescription(BATMAN_FACE_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(BATMAN_FACE_TEXT).assertHasNoClickAction()
        composeTestRule.onNodeWithText(ME_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithText(ME_TEXT).assertHasNoClickAction()
        composeTestRule.onNodeWithText(CONTACT_ME_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithText(CONTACT_ME_TEXT).assertHasClickAction()
    }

    @Test
    fun test_success_list_character() {
        composeTestRule.onNodeWithTag(LIST_TEST_TAG)
            .onChildren()
            .onFirst()
            .assert(hasText(FIRST_CHARACTER_NAME_TEXT))

        composeTestRule.onNodeWithTag(LIST_TEST_TAG)
            .onChildren()
            .onLast()
            .assert(hasText(LAST_CHARACTER_NAME_TEXT))
    }

    @Test
    fun test_error_detail_character() {
        composeTestRule.onNodeWithTag(LIST_TEST_TAG)
            .onChildren()
            .onFirst()
            .performClick()

        (marvelRepository as MarvelRepositoryFakeImpl).setShouldResponseError(true)
        composeTestRule.onNodeWithText(ERROR_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithText(ERROR_TEXT).assertHasNoClickAction()
        composeTestRule.onNodeWithText(ERROR_GO_BACK_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithText(ERROR_GO_BACK_TEXT).assertHasClickAction()
        composeTestRule.onNodeWithContentDescription(THANOS_FACE_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(THANOS_FACE_TEXT).assertHasNoClickAction()
    }

    @Test
    fun test_loading_detail_character() {
        composeTestRule.onNodeWithTag(LIST_TEST_TAG)
            .onChildren()
            .onFirst()
            .performClick()

        (marvelRepository as MarvelRepositoryFakeImpl).setShouldAwait(true)
        composeTestRule.onNodeWithContentDescription(AVENGERS_LOGO_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(AVENGERS_LOGO_TEXT).assertHasNoClickAction()
        composeTestRule.onNodeWithText(LOADING_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithText(LOADING_TEXT).assertHasNoClickAction()
    }

    @Test
    fun test_success_detail_character() {
        composeTestRule.onNodeWithTag(LIST_TEST_TAG)
            .onChildren()
            .onFirst()
            .performClick()

        composeTestRule.onNodeWithText(FIRST_CHARACTER_NAME_TEXT).assertIsDisplayed()
    }

    companion object {
        const val LOADING_TEXT = "Looking for the funniest heroes!"
        const val ERROR_TEXT = "Seems like Thanos won!"
        const val ERROR_GO_BACK_TEXT = "Click to go back!"
        const val ERROR_RETRY_TEXT = "Click to retry!"
        const val THANOS_FACE_TEXT = "Thanos' face"
        const val AVENGERS_LOGO_TEXT = "Avengers logo"
        const val BATMAN_FACE_TEXT = "Batman's face"
        const val ME_TEXT = "Gary Figuerola Mora"
        const val CONTACT_ME_TEXT = "Click here to contact me!"
        const val FIRST_CHARACTER_NAME_TEXT = "Spider-dok"
        const val LAST_CHARACTER_NAME_TEXT = "A-Bomb"
    }
}