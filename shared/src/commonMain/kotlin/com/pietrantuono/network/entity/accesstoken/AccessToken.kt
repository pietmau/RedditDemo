package com.pietrantuono.network.entity.accesstoken

import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    val access_token: String? = null,
    val token_type: String? = null,
    val device_id: String? = null,
    val expires_in: Int? = null,
    val scope: String? = null
)
