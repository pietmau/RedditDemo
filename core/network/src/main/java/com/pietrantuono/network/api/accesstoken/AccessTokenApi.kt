package com.pietrantuono.network.api.accesstoken

import com.pietrantuono.network.entity.accesstoken.AccessToken
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccessTokenApi {

    @FormUrlEncoded
    @POST("api/v1/access_token")
    fun getAccessToken(
        @Field(GRANT_TYPE) grantType: String,
        @Field(REDIRECT_URI) redirectUri: String,
        @Field(DEVICE_ID) deviceId: String,
    ): Call<AccessToken>

    private companion object {
        private const val GRANT_TYPE = "grant_type"
        private const val REDIRECT_URI = "redirect_uri"
        private const val DEVICE_ID = "device_id"
    }
}


