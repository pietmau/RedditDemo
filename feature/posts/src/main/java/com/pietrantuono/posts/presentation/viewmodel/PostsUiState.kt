package com.pietrantuono.posts.presentation.viewmodel

import com.pietrantuono.posts.presentation.viewmodel.NavigationDestination.None

data class PostsUiState(val posts: List<PostUiModel> = emptyList(), val navDestination: NavigationDestination = None)

data class PostUiModel(
    val id: String,
    val title: String? = null,
    val thumbnail: String? = null,
    val author: String? = null,
    val created: Long? = null,
)

sealed class NavigationDestination {
    object None : NavigationDestination()
    data class PostDetails(val postId: String) : NavigationDestination()
}