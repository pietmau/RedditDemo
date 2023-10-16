package com.pietrantuono.detail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.TurbineTestContext
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.pietrantuono.common.model.reddit.Post
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.GetPostDetail
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.ImageLoaded
import com.pietrantuono.posts.GetPostDetailUseCase
import com.pietrantuono.posts.GetPostDetailUseCase.Params
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
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
        coEvery { get<PostDetailUiModel>(POST) } returns null
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
            assertThat(state.loading).isTrue()
        }
    }

    @Test
    fun `given post is available when gets posts then emits the post`() = runTest {
        viewModel.uiState.test {
            // When
            viewModel.accept(GetPostDetail(TEXT))

            // Then
            assertModelIsEmitted()
            coVerify { useCase.execute(eq(Params(TEXT))) }
        }
    }

    @Test
    fun `given there is saved state when gets posts then gets it from handle`() = runTest {
        viewModel.uiState.test {
            // Given
            every { handle.get<PostDetailUiModel>(POST) } returns model

            // When
            viewModel.accept(GetPostDetail(TEXT))

            // Then
            assertModelIsEmitted()
            coVerify { useCase wasNot Called }
        }
    }

    @Test
    fun `when image is loaded then stops loading`() = runTest {
        viewModel.uiState.test {
            // When
            viewModel.accept(GetPostDetail(TEXT))
            viewModel.accept(ImageLoaded)

            // Then
            val state = expectMostRecentItem()
            assertThat(state.post).isEqualTo(model)
            assertThat(state.error).isFalse()
            assertThat(state.loading).isFalse()
        }
    }

    private fun TurbineTestContext<DetailUiState>.assertModelIsEmitted() {
        val state = expectMostRecentItem()
        assertThat(state.post).isEqualTo(model)
        assertThat(state.error).isFalse()
        assertThat(state.loading).isTrue()
    }

    private companion object {
        private const val POST = "post"
        private const val TEXT = "text"
    }
}
