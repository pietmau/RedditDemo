package com.pietrantuono.posts.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import coil.compose.AsyncImage
import com.pietrantuono.home.R
import com.pietrantuono.posts.presentation.viewmodel.PostUiModel

private const val TITLE = "Title"
private const val EMPTY_STRING = ""

@Composable
fun RedditCard(
    modifier: Modifier = Modifier,
    post: PostUiModel = PostUiModel(title = TITLE, id = EMPTY_STRING),
) {
    val smallPadding = dimensionResource(R.dimen.small_padding)
    Card(
        modifier = modifier
            .padding(
                top = smallPadding,
                start = smallPadding,
                end = smallPadding
            )
            .defaultMinSize(minHeight = dimensionResource(R.dimen.default_size))//TODO Externzalize
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(smallPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ThumbNailImage(post)
            Column(
                modifier = Modifier
                    .padding(
                        start = smallPadding,
                        end = smallPadding
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = post.author ?: EMPTY_STRING, // TODO add better default
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    modifier = Modifier.padding(top = smallPadding),
                    text = post.title ?: EMPTY_STRING, // TODO add better default
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
private fun ThumbNailImage(post: PostUiModel = PostUiModel(title = "Title", id = EMPTY_STRING)) {
    var hideImage by rememberSaveable { mutableStateOf(false) }
    if (!hideImage) {
        AsyncImage(
            modifier = Modifier
                .width(dimensionResource(R.dimen.default_size))
                .height(dimensionResource(R.dimen.default_size))
                .padding(dimensionResource(R.dimen.small_padding)),
            model = post.thumbnail,
            contentDescription = post.title,
            onError = { hideImage = true }
        )
    } else {
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.small_padding)))
    }
}

