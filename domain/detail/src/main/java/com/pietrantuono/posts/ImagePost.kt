package com.pietrantuono.posts

import com.pietrantuono.posts.ImagePost.Image.Empty

data class ImagePost(
    val id: String,
    val image: Image? = Empty,
    val kind: String? = null,
    val subreddit: String? = null,
    val thumbnail: String? = null,
    val title: String? = null,
    val ups: Int? = null,
    val created: Long? = null,
    val createdUtc: Long? = null,
    val subredditId: String? = null,
    val author: String? = null,
    val numComments: Int? = null,
    val permalink: String? = null,
    val url: String? = null,
    val score: Int? = null,
) {

    sealed class Image {
        object Empty : Image()
        data class ImageUrl(val url: String? = null) : Image()
    }

}

