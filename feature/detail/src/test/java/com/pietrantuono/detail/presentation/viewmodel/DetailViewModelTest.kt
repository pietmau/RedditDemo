package com.pietrantuono.detail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.pietrantuono.common.model.reddit.Post
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.GetPostDetail
import com.pietrantuono.posts.GetPostDetailUseCase
import com.pietrantuono.posts.GetPostDetailUseCase.Params
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DetailViewModelTest {
    private val post: Post = mockk()
    private val useCase: GetPostDetailUseCase = mockk {
        coEvery { execute(eq(Params(TEXT))) } returns post
    }
    private val model: PostDetailUiModel = mockk()
    private val mapper: DetailMapper = mockk {
        coEvery { map(post) } returns model
    }
    private val handle: SavedStateHandle = mockk(relaxed = true) {
        coEvery { get<String>(ID) } returns null
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
    private val viewModel = DetailViewModel(useCase, mapper, handle, testDispatcher, mockk())

    @Test
    fun `when starts then is loading`() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.error).isFalse()
            assertThat(state.post).isNull()
        }
    }

    @Test
    fun `given post is available when gets posts then emits the post`() = runTest {
        viewModel.uiState.test {
            // When
            viewModel.accept(GetPostDetail(TEXT))

            // Then
            val state = expectMostRecentItem()
            assertThat(state.post).isEqualTo(model)
            assertThat(state.error).isFalse()
        }
    }

    @Test
    fun `given there is no saved state when starts then emits the post`() = runTest {
        // Given
        every { handle.get<String>(ID) } returns TEXT

        // When
        val viewModel = DetailViewModel(useCase, mapper, handle, testDispatcher, mockk())

        // Then
        viewModel.uiState.test {
            val state = expectMostRecentItem()
            assertThat(state.post).isEqualTo(model)
            assertThat(state.error).isFalse()
        }
    }

    private companion object {
        private const val ID = "id"
        private const val TEXT = "text"
    }
}
