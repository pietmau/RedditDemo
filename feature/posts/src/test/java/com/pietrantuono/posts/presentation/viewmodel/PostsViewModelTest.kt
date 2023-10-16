package com.pietrantuono.posts.presentation.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.pietrantuono.posts.GetPostsUseCase
import com.pietrantuono.posts.presentation.viewmodel.NavigationDestination.None
import com.pietrantuono.posts.presentation.viewmodel.NavigationDestination.PostDetails
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.GetInitialPosts
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.GetNewPosts
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.NavigationPerformed
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.OnPostClicked
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PostsViewModelTest {
    private val useCase: GetPostsUseCase = mockk {
        coEvery { execute(any()) } returns listOf(mockk())
    }
    private val model: PostUiModel = mockk()
    private val mapper: PostsUiStateMapper = mockk {
        coEvery { map(any()) } returns model
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val coroutineContext = UnconfinedTestDispatcher()
    private val viewModel = PostsViewModel(
        useCase = useCase,
        mapper = mapper,
        coroutineContext = coroutineContext,
        logger = mockk(relaxed = true)
    )

    @Test
    fun `when starts then posts are empty`() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isTrue()
            assertThat(state.posts).isEmpty()
            assertThat(state.navDestination).isEqualTo(None)
        }
    }

    @Test
    fun `given posts are available when gets posts then posts are emitted`() = runTest {
        viewModel.uiState.test {
            // When
            viewModel.accept(GetInitialPosts)

            // Then
            val state = expectMostRecentItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.posts).containsExactly(model)
            coVerify { useCase.execute(any()) }
        }
    }

    @Test
    fun `when post clicked then navigates to post`() = runTest {
        viewModel.uiState.test {
            // When
            viewModel.accept(OnPostClicked(ID))

            // Then
            assertThat(expectMostRecentItem().navDestination).isEqualTo(PostDetails(ID))
        }
    }

    @Test
    fun `when post clicked then perform navigation then destination is reset`() = runTest {
        viewModel.uiState.test {
            // When
            viewModel.accept(OnPostClicked(ID))
            viewModel.accept(NavigationPerformed)

            // Then
            assertThat(expectMostRecentItem().navDestination).isEqualTo(None)
        }
    }

    @Test
    fun `given usecase throws and error when gets posts then stops loading`() = runTest {
        viewModel.uiState.test {
            // Given
            coEvery { useCase.execute(any()) } throws Exception()

            // When
            viewModel.accept(GetInitialPosts)

            // Then
            assertThat(awaitItem().isLoading).isTrue()
            assertThat(awaitItem().isLoading).isFalse()
        }
    }

    @Test
    fun `given posts are available when gets posts then updates loading`() = runTest {
        viewModel.uiState.test {
            // When
            viewModel.accept(GetInitialPosts)

            // Then
            assertThat(awaitItem().isLoading).isTrue()
            assertThat(awaitItem().isLoading).isFalse()
        }
    }

    @Test
    fun `given posts received when gets posts then calls usecase once`() = runTest {
        viewModel.uiState.test {
            // When
            viewModel.accept(GetInitialPosts)
            viewModel.accept(GetInitialPosts)

            // Then
            assertThat(awaitItem().isLoading).isTrue()
            assertThat(awaitItem().isLoading).isFalse()
            coVerify(exactly = 1) {
                useCase.execute(any())
            }
        }
    }

    @Test
    fun `given posts received when refresh posts then calls usecase again`() = runTest {
        viewModel.uiState.test {
            // When
            viewModel.accept(GetInitialPosts)
            viewModel.accept(GetNewPosts)

            // Then
            assertThat(awaitItem().isLoading).isTrue()
            assertThat(awaitItem().isLoading).isFalse()
            assertThat(awaitItem().isLoading).isTrue()
            assertThat(awaitItem().isLoading).isFalse()
            coVerify(exactly = 2) {
                useCase.execute(any())
            }
        }
    }

    private companion object {
        private const val ID = "id"
    }
}
