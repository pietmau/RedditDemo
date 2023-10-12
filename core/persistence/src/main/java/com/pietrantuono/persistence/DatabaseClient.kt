package com.pietrantuono.persistence

import com.pietrantuono.model.reddit.Post

interface DatabaseClient {
    suspend fun insertPosts(posts: List<Post>)

    suspend fun getPosts(): List<Post>
}