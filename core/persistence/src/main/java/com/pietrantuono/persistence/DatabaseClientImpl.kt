package com.pietrantuono.persistence

import androidx.room.withTransaction
import com.pietrantuono.posts.model.reddit.Post
import javax.inject.Inject

class DatabaseClientImpl @Inject constructor(
    private val redditDatabase: RedditDatabase,
    private val redditDao: RedditDao,
    private val postToPersistedPostEntityMapper: PostToPersistedPostEntityMapper,
    private val imageToPersistedImageEntityMapper: ImageToPersistedImageEntityMapper,
    private val postWithImagesEntityToPostMapper: PostWithImagesEntityToPostMapper,
) : DatabaseClient {

    override suspend fun insertPosts(posts: List<Post>) {
        redditDatabase.withTransaction {
            posts.forEach { post ->
                val key = redditDao.insert(postToPersistedPostEntityMapper.map(post))
                post.images
                    .map { imageToPersistedImageEntityMapper.map(it) }
                    .map { it.copy(postKey = key) }
                    .forEach { image ->
                        redditDao.insert(image)
                    }
            }
        }
    }

    override suspend fun getPosts(limit: Int) = redditDao.getPosts().map { postWithImagesEntityToPostMapper.map(it) }

}
