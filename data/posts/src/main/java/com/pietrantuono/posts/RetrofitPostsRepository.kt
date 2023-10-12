package com.pietrantuono.posts

import com.pietrantuono.network.api.reddit.RedditApiClient
import com.pietrantuono.network.networkchecker.NetworkChecker
import com.pietrantuono.persistence.DatabaseClient
import com.pietrantuono.posts.model.reddit.Post
import javax.inject.Inject

class RetrofitPostsRepository @Inject constructor(
    private val redditApi: RedditApiClient,
    private val networkDataEntityMapper: NetworkDataEntityMapper,
    private val networkChecker: NetworkChecker,
    private val databaseClient: DatabaseClient
) : PostsRepository {
    override suspend fun getPosts(subReddit: String, limit: Int): List<Post> {
        if (!networkChecker.isNetworkAvailable()) {
            return databaseClient.getPosts(limit)
        }
        val response = redditApi.getNewPosts(subReddit, limit)
        val posts = networkDataEntityMapper.map(response)
        databaseClient.insertPosts(posts) // TODO controlla!!!!!!!!!!!
        return posts
    }
}
