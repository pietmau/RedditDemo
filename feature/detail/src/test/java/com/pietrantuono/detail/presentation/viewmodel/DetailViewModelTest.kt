package com.pietrantuono.detail.presentation.viewmodel

import app.cash.turbine.TurbineTestContext
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.pietrantuono.common.model.reddit.Post
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.ErrorConsumed
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.GetPostDetail
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.ImageLoaded
import com.pietrantuono.detail.presentation.viewmodel.DetailUiEvent.OnError
import com.pietrantuono.detail.presentation.viewmodel.ErrorUiModel.Error
import com.pietrantuono.detail.presentation.viewmodel.ErrorUiModel.None
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
    private val detailMapper: DetailMapper = mockk {
        coEvery { map(post) } returns model
    }
    private val detailScreenState: DetailScreenState = mockk(relaxed = true) {
        coEvery { post } returns null
    }
    private val errorMapper: ErrorMapper = mockk(relaxed = true) {
        every { map(TEXT) } returns TEXT
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private
    val viewModel = DetailViewModel(
        useCase,
        detailMapper,
        errorMapper,
        mockk(),
        detailScreenState,
        testDispatcher,
        mockk()
    )

    @Test
    fun `when starts then is loading`() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.post).isNull()
            assertThat(state.loading).isTrue()
            assertThat(state.error).isEqualTo(None)
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
            every { detailScreenState.post } returns model

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
            assertThat(state.loading).isFalse()
        }
    }

    @Test
    fun `when error is received then sets it on the ui`() = runTest {
        viewModel.uiState.test {
            // When
            viewModel.accept(GetPostDetail(TEXT))
            viewModel.accept(OnError(TEXT))

            // Then
            val state = expectMostRecentItem()
            assertThat(state.post).isEqualTo(model)
            assertThat(state.loading).isFalse()
            assertThat(state.error).isEqualTo(Error(TEXT))
        }
    }

    @Test
    fun `when error is consumed then is removed from the ui`() = runTest {
        viewModel.uiState.test {
            // When
            viewModel.accept(GetPostDetail(TEXT))
            viewModel.accept(OnError(TEXT))
            viewModel.accept(ErrorConsumed)

            // Then
            val state = expectMostRecentItem()
            assertThat(state.post).isEqualTo(model)
            assertThat(state.loading).isFalse()
            assertThat(state.error).isEqualTo(None)
        }
    }

    private fun TurbineTestContext<DetailUiState>.assertModelIsEmitted() {
        val state = expectMostRecentItem()
        assertThat(state.post).isEqualTo(model)
        assertThat(state.loading).isTrue()
        assertThat(state.error).isEqualTo(None)
    }

    private companion object {
        private const val TEXT = "text"
    }
}
