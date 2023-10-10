package com.pietrantuono.posts.data

import com.pietrantuono.model.reddit.Post
import com.pietrantuono.network.api.reddit.RedditApi
import com.pietrantuono.posts.domain.PostsRepository
import javax.inject.Inject

class RetrofitPostsRepository @Inject constructor(
    private val redditApi: RedditApi,
    private val networkDataEntityMapper: NetworkDataEntityMapper
) :
    PostsRepository {

    override suspend fun getPosts(): List<Post> {
        TODO("Not yet implemented")
    }

}
