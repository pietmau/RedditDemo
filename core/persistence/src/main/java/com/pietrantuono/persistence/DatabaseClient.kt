package com.pietrantuono.persistence

import com.pietrantuono.common.model.reddit.Post

interface DatabaseClient {

    suspend fun insertPosts(posts: List<Post>)

    suspend fun getPosts(limit: Int): List<Post>

    suspend fun getPostById(id: String): Post?
}