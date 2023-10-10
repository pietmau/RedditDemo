package com.pietrantuono.network.api.reddit

import com.pietrantuono.network.entity.reddit.NetowrkRedditResponseEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RedditApi {

    @GET("/r/{subReddit}/new/")
    suspend fun getNewPosts(
        @Path(SUBREDDIT) subReddit: String,
        @QueryMap queryMap: Map<String, String>,
    ): NetowrkRedditResponseEntity


    private companion object {
        private const val SUBREDDIT = "subReddit"
    }
}
