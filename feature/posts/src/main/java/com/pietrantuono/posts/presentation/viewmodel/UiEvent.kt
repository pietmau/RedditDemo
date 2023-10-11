package com.pietrantuono.posts.presentation.viewmodel

sealed class UiEvent {

    object GetPosts : UiEvent()

    data class OnPostClicked(val postId: String) : UiEvent()

    object NavigationPerformed : UiEvent()
}