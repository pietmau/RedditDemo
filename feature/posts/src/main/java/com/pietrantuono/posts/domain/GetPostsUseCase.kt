package com.pietrantuono.posts.domain

import com.pietrantuono.common.UseCase
import com.pietrantuono.model.reddit.Post
import com.pietrantuono.posts.domain.GetPostsUseCase.Params
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: PostsRepository) : UseCase<Params, List<Post>> {

    override suspend fun invoke(params: Params): List<Post> {
        TODO("Not yet implemented")
    }

    sealed class Params {
        object Empty : Params()
    }
}
