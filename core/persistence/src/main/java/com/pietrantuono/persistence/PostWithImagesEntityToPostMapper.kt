package com.pietrantuono.persistence

import com.pietrantuono.common.Mapper
import com.pietrantuono.common.model.reddit.Post
import com.pietrantuono.persistence.entity.PersistedPostEntity
import javax.inject.Inject

class PostWithImagesEntityToPostMapper @Inject constructor() : Mapper<PersistedPostEntity, Post> {
    override fun map(input: PersistedPostEntity) = Post(
        title = input.title,
        author = input.author,
        created = input.created,
        thumbnail = input.thumbnail,
        url = input.url,
        score = input.score,
        permalink = input.permalink,
        kind = input.kind,
        subreddit = input.subreddit,
        subredditId = input.subredditId,
        id = input.id,
        numComments = input.numComments,
        ups = input.ups,
        createdUtc = input.createdUtc,
        urlOverriddenByDest = input.urlOverriddenByDest,
        selfText = input.selfText
    )
}
