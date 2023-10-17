package com.pietrantuono.posts.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.pietrantuono.common.Logger
import com.pietrantuono.common.RedditViewModel
import com.pietrantuono.posts.GetPostsUseCase
import com.pietrantuono.posts.GetPostsUseCase.Params
import com.pietrantuono.posts.presentation.viewmodel.NavigationDestination.None
import com.pietrantuono.posts.presentation.viewmodel.NavigationDestination.PostDetails
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.GetInitialPosts
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.GetNewPosts
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.NavigationPerformed
import com.pietrantuono.posts.presentation.viewmodel.PostsUiEvent.OnPostClicked
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val useCase: GetPostsUseCase,
    private val mapper: PostsUiStateMapper,
    private val handle: SavedStateHandle,
    coroutineContext: CoroutineContext,
    logger: Logger
) : RedditViewModel<PostsUiState, PostsUiEvent>(coroutineContext, logger) {

    override val _uiState: MutableStateFlow<PostsUiState> = MutableStateFlow(PostsUiState())

    override fun accept(event: PostsUiEvent) {
        when (event) {
            is GetInitialPosts -> getInitialPosts()
            is OnPostClicked -> navigateToPost(event.postId)
            is NavigationPerformed -> navigationPerformed()
            is GetNewPosts -> updatePosts { getNewPosts() }
        }
    }

    private fun navigationPerformed() {
        updateState { copy(navDestination = None) }
    }

    private fun navigateToPost(postId: String) {
        updateState { copy(navDestination = PostDetails(postId)) }
    }

    private fun getInitialPosts() {
        if (latestState.posts.isNotEmpty()) return
        updatePosts { handle[POSTS] ?: getNewPosts() }
    }

    private fun updatePosts(source: suspend () -> List<PostUiModel>) {
        updateState { copy(isLoading = true) }
        launch(
            block = {
                val posts = source()
                updateState {
                    copy(
                        isLoading = false,
                        posts = posts
                    )
                }
            },
            onError = { updateState { copy(isLoading = false) } }
        )
    }

    private suspend fun getNewPosts() =
        useCase.execute(Params())
            .map { mapper.map(it) }
            .also { handle[POSTS] = it }

    private companion object {
        private const val POSTS = "posts"
    }
}
