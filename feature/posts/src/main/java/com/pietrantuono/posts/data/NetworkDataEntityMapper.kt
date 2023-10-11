package com.pietrantuono.posts.data

import com.pietrantuono.common.Mapper
import com.pietrantuono.model.reddit.Image
import com.pietrantuono.model.reddit.Post
import com.pietrantuono.network.entity.reddit.NetowrkRedditResponseEntity
import javax.inject.Inject

class NetworkDataEntityMapper @Inject constructor() : Mapper<NetowrkRedditResponseEntity, List<Post>> {

    override fun map(input: NetowrkRedditResponseEntity): List<Post> {
        val posts = filterNullPosts(input)
        return posts.map { (kind, data) ->
            Post(kind = kind,
                name = data.name,
                subreddit = data.subreddit,
                thumbnail = data.thumbnail,
                title = data.title,
                ups = data.ups,
                created = data.created,
                images = data.preview?.images?.flatMap { it.resolutions }?.map {
                    Image(
                        url = it.url, width = it.width, height = it.height
                    )
                } ?: emptyList(),
                subredditId = data.subredditId,
                id = data.id ?: "",
                author = data.author,
                numComments = data.numComments,
                permalink = data.permalink,
                url = data.url,
                score = data.score,
                createdUtc = data.createdUtc)
        }
    }

    private fun filterNullPosts(input: NetowrkRedditResponseEntity) =
        input.data?.posts?.mapNotNull { post -> post.data?.let { data -> post.kind to data } } ?: emptyList()
}

