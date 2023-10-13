package com.pietrantuono.posts

import com.pietrantuono.common.UseCase
import com.pietrantuono.common.model.reddit.Post
import javax.inject.Inject

class GetPostDetailUseCase @Inject constructor(private val repository: PostDetailRepository) : UseCase<GetPostDetailUseCase.Params, Post?> {

    override suspend fun execute(params: Params) = repository.getPostDetail(params.postId)

    data class Params(val postId: String)
}
