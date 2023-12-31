package com.pietrantuono.network.api.reddit

import com.pietrantuono.network.interceptor.BearerTokenAuthInterceptor
import javax.inject.Inject
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRedditApiClient @Inject constructor(
    bearerTokenAuthInterceptor: BearerTokenAuthInterceptor
) : RedditApiClient {

    private val redditApi by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(bearerTokenAuthInterceptor)
            .build()
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RedditApi::class.java)
    }

    override suspend fun getNewPosts(
        subReddit: String,
        limit: Int
    ) = redditApi.getNewPosts(subReddit, mapOf(LIMIT to limit.toString()))

    private companion object {
        private const val BASEURL: String = "https://oauth.reddit.com"
        private const val LIMIT = "limit"
    }
}
