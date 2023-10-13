package com.pietrantuono.detail.presentation

import com.pietrantuono.detail.PostDetailRepositoryImpl
import com.pietrantuono.posts.PostDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface PostDetailModule {

    @Binds
    fun bindPostDetailRepository(repository: PostDetailRepositoryImpl): PostDetailRepository
}
