package com.pietrantuono.posts

import com.pietrantuono.common.model.reddit.Post

interface PostDetailRepository {

    suspend fun getPostDetail(id: String): Post?
}