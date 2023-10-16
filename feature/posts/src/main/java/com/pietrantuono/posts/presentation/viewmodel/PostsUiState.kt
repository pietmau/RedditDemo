package com.pietrantuono.posts.presentation.viewmodel

import android.os.Parcelable
import com.pietrantuono.posts.presentation.viewmodel.NavigationDestination.None
import kotlinx.android.parcel.Parcelize

data class PostsUiState(
    val isLoading: Boolean = true,
    val posts: List<PostUiModel> = emptyList(),
    val navDestination: NavigationDestination = None
)

@Parcelize
data class PostUiModel(
    val id: String,
    val title: String,
    val thumbnail: String? = null,
    val author: String,
    val created: Long? = null
) : Parcelable

sealed class NavigationDestination {
    object None : NavigationDestination()

    data class PostDetails(val postId: String) : NavigationDestination()
}
