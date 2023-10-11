package com.pietrantuono.posts.presentation.viewmodel

import com.pietrantuono.common.Mapper
import com.pietrantuono.model.reddit.Post

class UiStateMapper : Mapper<Post, PostUiModel> {

    override fun map(input: Post) =
        PostUiModel(
            id = input.id,
            title = input.title,
            author = input.author,
            created = input.created,
            images = input.images.map { image ->
                ImageUiModel(
                    url = image.url,
                    width = image.width,
                    height = image.height
                )
            }
        )
}