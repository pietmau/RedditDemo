package com.pietrantuono.posts.presentation.viewmodel

import com.pietrantuono.common.Mapper
import com.pietrantuono.common.model.reddit.Post
import javax.inject.Inject

class PostsUiStateMapper @Inject constructor() : Mapper<Post, PostUiModel> {

    override fun map(input: Post) =
        PostUiModel(
            id = input.id,
            title = input.title,
            author = input.author,
            created = input.created,
            thumbnail = input.thumbnail,
            images = input.images.map { image ->
                ImageUiModel(
                    url = image.url,
                    width = image.width,
                    height = image.height
                )
            }
        )
}