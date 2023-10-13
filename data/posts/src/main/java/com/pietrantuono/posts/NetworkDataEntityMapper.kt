package com.pietrantuono.posts

import com.pietrantuono.common.Mapper
import com.pietrantuono.network.entity.reddit.NetworkRedditResponseEntity
import com.pietrantuono.network.entity.reddit.NetworkDataEntity
import com.pietrantuono.common.model.reddit.Image
import com.pietrantuono.common.model.reddit.Post
import javax.inject.Inject

class NetworkDataEntityMapper @Inject constructor() : Mapper<NetworkRedditResponseEntity, List<Post>> {

    override fun map(input: NetworkRedditResponseEntity): List<Post> {
        val posts = filterNullPosts(input)
        return posts.map { (kind, data) ->
            Post(kind = kind,
                subreddit = data.subreddit,
                thumbnail = data.thumbnail,
                title = data.title,
                ups = data.ups,
                created = data.created,
                images = data.preview?.images?.flatMap { it.resolutions }?.map {
                    Image(
                        url = it.url,
                        width = it.width,
                        height = it.height
                    )
                } ?: emptyList(),
                subredditId = data.subredditId,
                id = data.id,
                author = data.author,
                numComments = data.numComments,
                permalink = data.permalink,
                url = data.url,
                score = data.score,
                createdUtc = data.createdUtc)
        }
    }

    private fun filterNullPosts(input: NetworkRedditResponseEntity): List<Pair<String?, NetworkDataEntity>> =
        input.data.posts.mapNotNull { post -> post.data?.let { data -> post.kind to data } } // TODO CLEAN THIS UP
}
