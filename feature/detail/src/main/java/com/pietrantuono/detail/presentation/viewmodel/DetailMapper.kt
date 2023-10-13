package com.pietrantuono.detail.presentation.viewmodel

import com.pietrantuono.common.Mapper
import com.pietrantuono.common.model.reddit.Post
import javax.inject.Inject

class DetailMapper @Inject constructor() : Mapper<Post, PostDetailUiModel> {
    override fun map(input: Post) =
        PostDetailUiModel(
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