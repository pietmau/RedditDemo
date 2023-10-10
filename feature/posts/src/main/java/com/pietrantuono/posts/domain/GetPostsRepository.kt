package com.pietrantuono.posts.domain

import com.pietrantuono.model.reddit.Post

interface GetPostsRepository {

    suspend fun getPosts(): List<Post>
}