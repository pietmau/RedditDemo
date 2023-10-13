package com.pietrantuono.persistence

import com.pietrantuono.common.Mapper
import com.pietrantuono.common.model.reddit.Post
import com.pietrantuono.persistence.entity.PersistedPostEntity
import javax.inject.Inject

class PostWithImagesEntityToPostMapper @Inject constructor() : Mapper<PersistedPostEntity, Post> {
    override fun map(post: PersistedPostEntity) = Post(
        title = post.title,
        author = post.author,
        created = post.created,
        thumbnail = post.thumbnail,
        url = post.url,
        score = post.score,
        permalink = post.permalink,
        kind = post.kind,
        subreddit = post.subreddit,
        subredditId = post.subredditId,
        id = post.id,
        numComments = post.numComments,
        ups = post.ups,
        createdUtc = post.createdUtc,
        urlOverriddenByDest = post.urlOverriddenByDest
    )
}
