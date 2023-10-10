package com.pietrantuono.posts.presentation

import androidx.lifecycle.ViewModel
import com.pietrantuono.posts.domain.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val useCase: GetPostsUseCase) : ViewModel()
