package com.pietrantuono.network.entity.reddit

import com.google.gson.annotations.SerializedName

data class NetworkRedditResponseEntity(
    @SerializedName("data") var data: NetworkPostsEntity = NetworkPostsEntity()
)
