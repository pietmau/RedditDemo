package com.pietrantuono.network.entity.accesstoken

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("access_token") val accessToken: String? = null,
    @SerializedName("token_type") val tokenType: String? = null,
    @SerializedName("device_id") val deviceId: String? = null,
    @SerializedName("expires_in") val expiresIn: Int? = null,
    @SerializedName("scope") val scope: String? = null,
)
