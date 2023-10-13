package com.pietrantuono.persistence

import com.pietrantuono.common.Mapper
import com.pietrantuono.persistence.entity.PostWithImagesEntity
import com.pietrantuono.common.model.reddit.Image
import com.pietrantuono.common.model.reddit.Post
import javax.inject.Inject

class PostWithImagesEntityToPostMapper @Inject constructor() : Mapper<PostWithImagesEntity, Post> {
    override fun map(input: PostWithImagesEntity) =
        Post(
            title = input.post.title,
            author = input.post.author,
            created = input.post.created,
            thumbnail = input.post.thumbnail,
            url = input.post.url,
            score = input.post.score,
            permalink = input.post.permalink,
            kind = input.post.kind,
            subreddit = input.post.subreddit,
            subredditId = input.post.subredditId,
            id = input.post.id,
            numComments = input.post.numComments,
            ups = input.post.ups,
            images = input.images.map {
                Image(
                    url = it.url,
                    width = it.width,
                    height = it.height
                )
            },
            createdUtc = input.post.createdUtc
        )
}
