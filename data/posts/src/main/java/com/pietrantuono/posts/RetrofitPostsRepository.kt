package com.pietrantuono.posts

import com.pietrantuono.common.model.reddit.Post
import com.pietrantuono.network.api.reddit.RedditApiClient
import com.pietrantuono.network.networkchecker.NetworkChecker
import com.pietrantuono.persistence.DatabaseClient
import javax.inject.Inject

class RetrofitPostsRepository @Inject constructor(
    private val redditApi: RedditApiClient,
    private val networkDataEntityMapper: NetworkDataEntityMapper,
    private val networkChecker: NetworkChecker,
    private val databaseClient: DatabaseClient,
) : PostsRepository {

    override suspend fun getPosts(
        subReddit: String,
        limit: Int,
    ): List<Post> {
        if (!networkChecker.isNetworkAvailable()) {
            return databaseClient.getPosts(limit)
        }
        val posts = networkDataEntityMapper.map(redditApi.getNewPosts(subReddit, limit))
        databaseClient.insertPosts(posts)
        return databaseClient.getPosts(limit)
    }
}
