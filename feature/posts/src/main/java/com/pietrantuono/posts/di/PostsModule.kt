package com.pietrantuono.posts.di

import com.pietrantuono.network.api.reddit.RedditApiClient
import com.pietrantuono.network.api.reddit.RetrofitRedditApiClient
import com.pietrantuono.posts.data.RetrofitPostsRepository
import com.pietrantuono.posts.domain.PostsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface PostsModule {

    @Binds
    fun bindPostsRepository(postsRepositoryImpl: RetrofitPostsRepository): PostsRepository

    @Binds
    fun bindRedditApi(client: RetrofitRedditApiClient): RedditApiClient
}