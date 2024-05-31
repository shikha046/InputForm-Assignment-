package com.example.assignment

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignment.ViewModel.UserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class UserInputScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockUserViewModel: UserViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mockUserViewModel = mock(UserViewModel::class.java)
    }

    @Test
    fun testUIElementsDisplayedCorrectly() = runTest {
        composeTestRule.setContent {
            UserInputScreen(userViewModel = mockUserViewModel)
        }

        composeTestRule.onNodeWithText("User Information").assertIsDisplayed()
        composeTestRule.onNodeWithText("Enter your details").assertIsDisplayed()
        composeTestRule.onNodeWithText("Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Age").assertIsDisplayed()
        composeTestRule.onNodeWithText("Date of Birth").assertIsDisplayed()
        composeTestRule.onNodeWithText("Address").assertIsDisplayed()
        composeTestRule.onNodeWithText("Save").assertIsDisplayed()
    }


    @Test
    fun testSaveButton() {

        composeTestRule.setContent {
            UserInputScreen(userViewModel = mockUserViewModel)
        }


        composeTestRule.onNodeWithText("Save").performClick()

    }

    @Test
    fun testDatePickerInteraction() {

        composeTestRule.setContent {
            UserInputScreen(userViewModel = mockUserViewModel)
        }

        composeTestRule.onNodeWithContentDescription("Pick Date").performClick()

        composeTestRule.onNodeWithContentDescription("Pick Date").assertExists()

    }

    @Test
    fun testEnterName() {
        composeTestRule.setContent {
            UserInputScreen(userViewModel=mockUserViewModel)
        }

        composeTestRule.onNodeWithText("Name")
            .performTextInput("Shikha")

        composeTestRule.onNodeWithText("Shikha")
            .assertExists()
    }

    @Test
    fun testEnterAge() {
        composeTestRule.setContent {
            UserInputScreen(userViewModel=mockUserViewModel)
        }

        composeTestRule.onNodeWithText("Age")
            .performTextInput("22")

        composeTestRule.onNodeWithText("22")
            .assertExists()
    }
    @Test
    fun testEnterAddress() {
        composeTestRule.setContent {
            UserInputScreen(userViewModel=mockUserViewModel)
        }

        composeTestRule.onNodeWithText("Address")
            .performTextInput("chaubepur")

        composeTestRule.onNodeWithText("chaubepur")
            .assertExists()
    }


}


