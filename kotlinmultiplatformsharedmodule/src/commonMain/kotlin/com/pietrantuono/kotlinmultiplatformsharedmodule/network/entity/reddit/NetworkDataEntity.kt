package com.pietrantuono.network.entity.reddit

data class NetworkDataEntity(
    val id: String,
    val score: Int? = null,
    val subreddit: String? = null,
    val thumbnail: String? = null,
    val title: String? = null,
    val ups: Int? = null,
    val created: Long? = null,
    val subredditId: String? = null,
    val author: String? = null,
    val numComments: Int? = null,
    val permalink: String? = null,
    val url: String? = null,
    val createdUtc: Long,
    val urlOverriddenByDest: String? = null,
    val selfText: String? = null
)
