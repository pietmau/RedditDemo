package com.pietrantuono.network.entity.reddit

import com.google.gson.annotations.SerializedName

data class NetworkPostEntity(
    @SerializedName("kind") val kind: String? = null,
    @SerializedName("data") val data: NetworkDataEntity? = null,
)
