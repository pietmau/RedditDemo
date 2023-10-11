package com.pietrantuono.posts.presentation.viewmodel

import com.pietrantuono.common.Logger
import com.pietrantuono.common.RedditViewModel
import com.pietrantuono.posts.domain.GetPostsUseCase
import com.pietrantuono.posts.domain.GetPostsUseCase.Params
import com.pietrantuono.posts.presentation.viewmodel.NavigationDestination.None
import com.pietrantuono.posts.presentation.viewmodel.NavigationDestination.PostDetails
import com.pietrantuono.posts.presentation.viewmodel.UiEvent.GetPosts
import com.pietrantuono.posts.presentation.viewmodel.UiEvent.NavigationPerformed
import com.pietrantuono.posts.presentation.viewmodel.UiEvent.OnPostClicked
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val useCase: GetPostsUseCase,
    private val mapper: UiStateMapper,
    coroutineContext: CoroutineContext,
    logger: Logger,
) : RedditViewModel<UiState, UiEvent>(coroutineContext, logger) {

    override val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())

    override fun accept(event: UiEvent) {
        when (event) {
            is GetPosts -> getInitialPosts()
            is OnPostClicked -> navigateToPost(event.postId)
            is NavigationPerformed -> navigationPerformed()
        }
    }

    private fun navigationPerformed() {
        updateState { copy(navDestination = None) }
    }

    private fun navigateToPost(postId: String) {
        updateState { copy(navDestination = PostDetails(postId)) }
    }

    private fun getInitialPosts() {
        launch {
            val posts = useCase.execute(Params())
            updateState { copy(posts = posts.map { mapper.map(it) }) }
        }
    }
}
