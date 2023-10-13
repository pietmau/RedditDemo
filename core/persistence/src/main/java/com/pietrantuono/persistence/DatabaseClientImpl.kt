package com.pietrantuono.persistence

import androidx.room.withTransaction
import com.pietrantuono.common.model.reddit.Post
import javax.inject.Inject

class DatabaseClientImpl @Inject constructor(
    private val redditDatabase: RedditDatabase,
    private val redditDao: RedditDao,
    private val postToPersistedPostEntityMapper: PostToPersistedPostEntityMapper,
    private val postWithImagesEntityToPostMapper: PostWithImagesEntityToPostMapper,
) : DatabaseClient {

    override suspend fun insertPosts(posts: List<Post>) {
        redditDatabase.withTransaction {
            posts.forEach { post ->
                redditDao.insert(postToPersistedPostEntityMapper.map(post)) // TODO: 2021-10-17 insert all at once
            }
        }
    }

    override suspend fun getPosts(limit: Int) = redditDao.getPosts().map { postWithImagesEntityToPostMapper.map(it) }

    override suspend fun getPostById(id: String): Post? = redditDao.getPostById(id)?.let { postWithImagesEntityToPostMapper.map(it) }
}
