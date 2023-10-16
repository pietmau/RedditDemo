package com.pietrantuono.detail.presentation.viewmodel

import com.pietrantuono.common.EMPTY_STRING
import com.pietrantuono.common.Mapper
import com.pietrantuono.common.model.reddit.Post
import javax.inject.Inject

class DetailMapper @Inject constructor() : Mapper<Post, PostDetailUiModel> {
    override fun map(input: Post) =
        PostDetailUiModel(
            id = input.id,
            title = input.title ?: EMPTY_STRING,
            author = input.author ?: EMPTY_STRING,
            created = input.created,
            image = input.urlOverriddenByDest,
            text = input.selfText ?: EMPTY_STRING
        )
}
