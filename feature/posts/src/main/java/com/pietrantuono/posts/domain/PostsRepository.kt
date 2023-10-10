package com.pietrantuono.posts.domain

import com.pietrantuono.model.reddit.Post

interface PostsRepository {

    suspend fun getPosts(): List<Post>
}