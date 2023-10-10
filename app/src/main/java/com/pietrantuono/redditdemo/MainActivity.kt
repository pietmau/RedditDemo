package com.pietrantuono.redditdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.pietrantuono.redditdemo.ui.theme.RedditDemoTheme
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import  com.pietrantuono.posts.PostsViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RedditDemoTheme {
                val viewmodel = hiltViewModel<PostsViewModel>()
            }
        }
    }
}
