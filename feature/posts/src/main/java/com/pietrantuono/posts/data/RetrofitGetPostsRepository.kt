package com.pietrantuono.posts.data

import com.pietrantuono.model.reddit.Post
import com.pietrantuono.posts.domain.GetPostsRepository

class RetrofitGetPostsRepository: GetPostsRepository {

    override suspend fun getPosts(): List<Post> {
        TODO("Not yet implemented")
    }

}
