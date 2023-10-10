package com.pietrantuono.posts.data

import com.pietrantuono.model.reddit.Post
import com.pietrantuono.network.api.reddit.RedditApiClient
import com.pietrantuono.posts.domain.PostsRepository
import javax.inject.Inject

class RetrofitPostsRepository @Inject constructor(
    private val redditApi: RedditApiClient,
    private val networkDataEntityMapper: NetworkDataEntityMapper,
) :
    PostsRepository {

    override suspend fun getPosts(subReddit: String): List<Post> = networkDataEntityMapper.map(redditApi.getNewPosts(subReddit))


}
