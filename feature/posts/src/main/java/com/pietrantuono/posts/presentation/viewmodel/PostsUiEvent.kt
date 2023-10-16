package com.pietrantuono.posts.presentation.viewmodel

sealed class PostsUiEvent {

    object GetInitialPosts : PostsUiEvent()

    data class OnPostClicked(val postId: String) : PostsUiEvent()

    object NavigationPerformed : PostsUiEvent()

    object GetNewPosts : PostsUiEvent()
}
