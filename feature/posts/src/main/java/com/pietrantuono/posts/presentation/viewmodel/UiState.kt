package com.pietrantuono.posts.presentation.viewmodel

data class UiState(val posts: List<PostUiModel> = emptyList())

data class PostUiModel(
    val id: String,
    val title: String? = null,
    val thumbnail: String? = null,
    val images: List<ImageUiModel> = emptyList(),
    val author: String? = null,
    val created: Long? = null,
)

data class ImageUiModel(
    val url: String? = null,
    val width: Int? = null,
    val height: Int? = null,
)