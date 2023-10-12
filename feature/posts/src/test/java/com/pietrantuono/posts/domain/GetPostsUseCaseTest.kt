package com.pietrantuono.posts.domain

import com.google.common.truth.Truth.assertThat
import com.pietrantuono.posts.model.reddit.Post
import com.pietrantuono.posts.domain.GetPostsUseCase.Params
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetPostsUseCaseTest {
    private val post = mockk<Post>()
    private val repository: PostsRepository = mockk {
        coEvery { getPosts(any()) } returns listOf(post)
    }
    private val useCase = GetPostsUseCase(repository)

    @Test
    fun `when executes then returns posts`() = runTest {
        // When
        val result = useCase.execute(Params())

        // Then
        assertThat(result).containsExactly(post)
    }
}