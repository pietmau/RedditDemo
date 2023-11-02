package com.pietrantuono.network.api.accesstoken

import com.pietrantuono.network.entity.accesstoken.AccessToken


interface AccessTokenApiClient {

    suspend fun getAccessToken(deviceId: String): AccessToken?
}
