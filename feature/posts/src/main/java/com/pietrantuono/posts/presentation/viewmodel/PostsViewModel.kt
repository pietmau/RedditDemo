package com.pietrantuono.posts.presentation.viewmodel

import com.pietrantuono.common.Logger
import com.pietrantuono.common.RedditViewModel
import com.pietrantuono.posts.domain.GetPostsUseCase
import com.pietrantuono.posts.domain.GetPostsUseCase.Params
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
            is UiEvent.GetPosts -> getInitialPosts()
        }
    }

    private fun getInitialPosts() {
        launch {
            val posts = useCase.invoke(Params())
            updateState { copy(posts = posts.map { mapper.map(it) }) }
        }
    }
}
