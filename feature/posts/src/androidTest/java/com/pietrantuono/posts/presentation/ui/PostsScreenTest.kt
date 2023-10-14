package com.pietrantuono.posts.presentation.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.pietrantuono.common.EMPTY_STRING
import com.pietrantuono.posts.presentation.viewmodel.PostUiModel
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.OnPostClicked
import com.pietrantuono.posts.presentation.viewmodel.PostsUiState
import org.junit.Rule
import org.junit.Test

class PostsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenIsLoadingThenProgressIsShown() {
        // When
        composeTestRule.setContent {
            PostsScreen(postsUiState = PostsUiState())
        }

        // Then
        composeTestRule.onNodeWithContentDescription(LOADING).assertExists()
    }

    @Test
    fun whenThereIsAPostThenAPostIsShown() {
        // When
        composeTestRule.setContent {
            PostsScreen(
                postsUiState = PostsUiState(
                    isLoading = false,
                    posts = listOf(
                        PostUiModel(
                            id = EMPTY_STRING,
                            title = TITLE,
                            author = EMPTY_STRING,
                        )
                    )
                )
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription(LOADING).assertDoesNotExist()
        composeTestRule.onNodeWithText(TITLE).assertExists()
    }

    @Test
    fun whenUserClicksThenAnEventIsTriggered() {
        // Given
        var event: PostsUiEvent? = null

        // When
        composeTestRule.setContent {
            PostsScreen(
                postsUiState = PostsUiState(
                    isLoading = false,
                    posts = listOf(
                        PostUiModel(
                            id = EMPTY_STRING,
                            title = TITLE,
                            author = EMPTY_STRING
                        )
                    )
                )
            ) {
                event = it
            }
        }

        // Then
        composeTestRule.onNodeWithText(TITLE).performClick()
        assert(event is OnPostClicked)
    }

    private companion object {
        private const val LOADING = "Loading"
        private const val TITLE = "title"
    }
}
