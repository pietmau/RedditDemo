package com.pietrantuono.network.entity.reddit

import com.google.gson.annotations.SerializedName

data class NetworkImagesEntity(
    @SerializedName("resolutions") val resolutions: List<NetworkResolutions> = emptyList(),
)

data class NetworkResolutions(
    @SerializedName("url") val url: String? = null,
    @SerializedName("width") val width: Int? = null,
    @SerializedName("height") val height: Int? = null
)