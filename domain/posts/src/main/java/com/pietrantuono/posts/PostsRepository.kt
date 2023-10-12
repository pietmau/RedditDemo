package com.pietrantuono.posts

import com.pietrantuono.posts.model.reddit.Post

interface PostsRepository {

    suspend fun getPosts(subReddit: String): List<Post>
}