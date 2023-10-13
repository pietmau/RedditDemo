package com.pietrantuono.network.entity.reddit

import com.google.gson.annotations.SerializedName

data class NetworkPostsEntity(
    @SerializedName("children") val posts: List<NetworkPostEntity> = emptyList(),
)
