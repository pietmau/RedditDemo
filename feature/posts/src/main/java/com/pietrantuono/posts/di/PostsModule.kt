package com.pietrantuono.posts.di

import com.pietrantuono.analytics.AndroidLogger
import com.pietrantuono.common.Logger
import com.pietrantuono.network.api.accesstoken.RetrofitAccessTokenApiClient
import com.pietrantuono.network.api.reddit.RedditApiClient
import com.pietrantuono.network.api.reddit.RetrofitRedditApiClient
import com.pietrantuono.network.interceptor.BearerTokenAuthInterceptor
import com.pietrantuono.network.tokenmanager.TokenManager
import com.pietrantuono.posts.data.RetrofitPostsRepository
import com.pietrantuono.posts.domain.PostsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
interface PostsModule {

    @Binds
    fun bindPostsRepository(postsRepositoryImpl: RetrofitPostsRepository): PostsRepository

    @Binds
    fun bindRedditApi(client: RetrofitRedditApiClient): RedditApiClient

    @Binds
    fun bindLogger(logger: AndroidLogger): Logger

    companion object {

        @Provides
        fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO

    }
}