package com.pietrantuono.network.api.reddit

import com.pietrantuono.network.entity.reddit.NetowrkRedditResponseEntity

interface RedditApiClient {

    suspend fun getNewPosts(subReddit: String): NetowrkRedditResponseEntity
}