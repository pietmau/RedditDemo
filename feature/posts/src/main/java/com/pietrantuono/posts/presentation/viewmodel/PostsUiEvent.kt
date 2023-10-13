package com.pietrantuono.posts.presentation.viewmodel

sealed class PostsUiEvent {

    object GetPosts : PostsUiEvent()

    data class OnPostClicked(val postId: String) : PostsUiEvent()

    object NavigationPerformed : PostsUiEvent()
}
