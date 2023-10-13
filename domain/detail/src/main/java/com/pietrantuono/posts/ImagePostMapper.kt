package com.pietrantuono.posts

import com.pietrantuono.common.Mapper
import com.pietrantuono.common.model.reddit.Post
import javax.inject.Inject

class ImagePostMapper @Inject constructor() : Mapper<Post, ImagePost> {
    override fun map(input: Post) = ImagePost(
        id = input.id,
        kind = input.kind,
        subreddit = input.subreddit,
        thumbnail = input.thumbnail,
        title = input.title,
        ups = input.ups,
        created = input.created,
        createdUtc = input.createdUtc,
        subredditId = input.subredditId,
        author = input.author,
        numComments = input.numComments,
        permalink = input.permalink,
        url = input.url,
        score = input.score,
    )
}
