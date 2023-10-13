package com.pietrantuono.common.model.reddit

data class Post(
    val id: String,
    val kind: String? = null,
    val subreddit: String? = null,
    val thumbnail: String? = null,
    val title: String? = null,
    val ups: Int? = null,
    val created: Long? = null,
    val createdUtc: Long? = null,
    val images: List<Image> = emptyList(),
    val subredditId: String? = null,
    val author: String? = null,
    val numComments: Int? = null,
    val permalink: String? = null,
    val url: String? = null,
    val score: Int? = null,
)