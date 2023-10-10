package com.pietrantuono.network.entity.reddit

import com.google.gson.annotations.SerializedName

data class NetworkPostsEntity(
    @SerializedName("after") val after: String? = null,
    @SerializedName("children") val posts: List<NetworkPostEntity> = emptyList(),
    @SerializedName("before") val before: String? = null
)