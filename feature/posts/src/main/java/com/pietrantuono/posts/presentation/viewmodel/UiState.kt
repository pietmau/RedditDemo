package com.pietrantuono.posts.presentation.viewmodel

import com.pietrantuono.posts.presentation.viewmodel.NavigationDestination.None

data class UiState(val posts: List<PostUiModel> = emptyList(), val navDestination: NavigationDestination = None)

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

sealed class NavigationDestination {
    object None : NavigationDestination()
    data class PostDetails(val postId: String) : NavigationDestination()
}