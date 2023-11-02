package com.pietrantuono.network.entity.accesstoken

import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    val accessToken: String? = null,
    val tokenType: String? = null,
    val deviceId: String? = null,
    val expiresIn: Int? = null,
    val scope: String? = null
)
