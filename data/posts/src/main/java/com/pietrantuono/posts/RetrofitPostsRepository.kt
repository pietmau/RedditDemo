package com.pietrantuono.posts

import com.pietrantuono.common.Logger
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
    private val logger: Logger,
) : PostsRepository {

    override suspend fun getPosts(
        subReddit: String,
        limit: Int,
    ): List<Post> {
        if (!networkChecker.isNetworkAvailable()) {
            return databaseClient.getPosts(limit)
        }
        return try {
            val posts = networkDataEntityMapper.map(redditApi.getNewPosts(subReddit, limit))
            databaseClient.insertPosts(posts)
            databaseClient.getPosts(limit)
        } catch (e: Exception) {
            logger.logException(e)
            databaseClient.getPosts(limit)
        }
    }
}
