package com.pietrantuono.network.api.reddit

import com.pietrantuono.network.entity.reddit.NetworkRedditResponseEntity

interface RedditApiClient {

    suspend fun getNewPosts(
        subReddit: String,
        limit: Int
    ): NetworkRedditResponseEntity
}
