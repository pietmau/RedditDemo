package com.pietrantuono.posts.presentation.viewmodel

import com.pietrantuono.common.EMPTY_STRING
import com.pietrantuono.common.Mapper
import com.pietrantuono.common.model.reddit.Post
import javax.inject.Inject

class PostsUiStateMapper @Inject constructor() : Mapper<Post, PostUiModel> {

    override fun map(input: Post) =
        PostUiModel(
            id = input.id,
            title = input.title ?: EMPTY_STRING,
            author = input.author ?: EMPTY_STRING,
            created = input.created,
            thumbnail = input.thumbnail
        )
}
