package com.pietrantuono.posts

import com.pietrantuono.common.UseCase
import com.pietrantuono.common.model.reddit.Post
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: PostsRepository
) : UseCase<GetPostsUseCase.Params, List<Post>> {

    override suspend fun execute(params: Params) = repository.getPosts(
        params.subReddit,
        params.limit
    )

    data class Params(val subReddit: String = DEFAULT_SUBREDDIT, val limit: Int = DEFAULT_LIMIT) {
        private companion object {
            private const val DEFAULT_SUBREDDIT = "pics"
            private const val DEFAULT_LIMIT = 100
        }
    }
}
