package com.pietrantuono.posts

import com.pietrantuono.common.UseCase
import com.pietrantuono.posts.model.reddit.Post
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: PostsRepository) : UseCase<GetPostsUseCase.Params, List<Post>> {

    override suspend fun execute(params: Params) = repository.getPosts(params.subReddit)

    data class Params(val subReddit: String = "pics")
}
