package com.pietrantuono.persistence

import com.pietrantuono.common.model.reddit.Post
import javax.inject.Inject

class DatabaseClientImpl @Inject constructor(
    private val redditDao: RedditDao,
    private val postToPersistedPostEntityMapper: PostToPersistedPostEntityMapper,
    private val postWithImagesEntityToPostMapper: PostWithImagesEntityToPostMapper
) : DatabaseClient {

    override suspend fun insertPosts(posts: List<Post>) {
        redditDao.insertAll(posts.map { postToPersistedPostEntityMapper.map(it) })
    }

    override suspend fun getPosts(limit: Int) = redditDao.getPosts(limit).map {
        postWithImagesEntityToPostMapper.map(it)
    }

    override suspend fun getPostById(id: String): Post? = redditDao.getPostById(id)?.let {
        postWithImagesEntityToPostMapper.map(it)
    }
}
