package com.pietrantuono.network.entity.reddit

import com.google.gson.annotations.SerializedName

data class NetworkDataEntity(
    @SerializedName("score") val score: Int? = null,
    @SerializedName("subreddit") val subreddit: String? = null,
    @SerializedName("thumbnail") val thumbnail: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("ups") val ups: Int? = null,
    @SerializedName("created") val created: Long? = null,
    @SerializedName("preview") val preview: NetworkPreviewEntity? = NetworkPreviewEntity(),
    @SerializedName("subreddit_id") val subredditId: String? = null,
    @SerializedName("id") val id: String,
    @SerializedName("author") val author: String? = null,
    @SerializedName("num_comments") val numComments: Int? = null,
    @SerializedName("permalink") val permalink: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("created_utc") val createdUtc: Long
)