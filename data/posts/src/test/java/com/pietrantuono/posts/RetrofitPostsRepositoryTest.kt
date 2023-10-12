package com.pietrantuono.posts

import com.google.common.truth.Truth.assertThat
import com.pietrantuono.network.api.reddit.RedditApiClient
import com.pietrantuono.network.entity.reddit.NetworkRedditResponseEntity
import com.pietrantuono.network.networkchecker.NetworkChecker
import com.pietrantuono.persistence.DatabaseClient
import com.pietrantuono.posts.model.reddit.Post
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RetrofitPostsRepositoryTest {
    private val post: Post = mockk()
    private val mNetworkRedditResponseEntity: NetworkRedditResponseEntity = mockk()
    private val redditApi: RedditApiClient = mockk(relaxed = true) {
        coEvery { getNewPosts(any(), any()) } returns mNetworkRedditResponseEntity
    }
    private val networkDataEntityMapper: NetworkDataEntityMapper = mockk(relaxed = true) {
        coEvery { map(any()) } returns listOf(post)
    }
    private val networkChecker: NetworkChecker = mockk(relaxed = true)
    private val databaseClient: DatabaseClient = mockk(relaxed = true) {
        coEvery { getPosts(any()) } returns listOf(post)
    }
    private val repository = RetrofitPostsRepository(
        redditApi,
        networkDataEntityMapper,
        networkChecker,
        databaseClient
    )

    @Test
    fun `given network is not available when gets posts then returns posts from database`() = runTest {
        // Given
        coEvery { networkChecker.isNetworkAvailable() } returns false

        // When
        val result = repository.getPosts(SUBREDDIT, LIMIT)

        // Then
        coVerify { databaseClient.getPosts(any()) }
        coVerify { redditApi wasNot Called }
        assertThat(result).isEqualTo(listOf(post))
    }

    @Test
    fun `given network is available when gets posts then saves the posts`() = runTest {
        // Given
        coEvery { networkChecker.isNetworkAvailable() } returns true

        // When
        val result = repository.getPosts(SUBREDDIT, LIMIT)

        // Then
        coVerify {
            databaseClient.insertPosts(any())
            databaseClient.getPosts(any())
            redditApi.getNewPosts(eq(SUBREDDIT), eq(LIMIT))
        }
        assertThat(result).isEqualTo(listOf(post))
    }

    private companion object {
        private const val SUBREDDIT = "SUBREDDIT"
        const val LIMIT = 10
    }
}
