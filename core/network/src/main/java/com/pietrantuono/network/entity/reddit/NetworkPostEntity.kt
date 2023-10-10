package com.pietrantuono.network.entity.reddit

import com.google.gson.annotations.SerializedName
import com.pietrantuono.network.entity.reddit.NetworkDataEntity

data class NetworkPostEntity(
    @SerializedName("kind") val kind: String? = null,
    @SerializedName("data") val data: NetworkDataEntity? = null
)