package com.pietrantuono.network

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.pietrantuono.common.Logger
import com.pietrantuono.network.api.accesstoken.RetrofitAccessTokenApiClient
import com.pietrantuono.network.interceptor.BasicAuthInterceptor
import com.pietrantuono.network.interceptor.BearerTokenAuthInterceptor
import com.pietrantuono.network.tokenmanager.SharedPreferencesTokenManager
import com.pietrantuono.network.tokenmanager.TokenManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
interface NetworkModule {

    @Binds
    fun bindTokenManager(tokenManagerImpl: SharedPreferencesTokenManager): TokenManager

    companion object {

        @Provides
        fun provideBearerTokenAuthInterceptor(
            tokenManager: TokenManager,
            accessTokenApiClient: RetrofitAccessTokenApiClient,
            logger: Logger,
        ) = BearerTokenAuthInterceptor(
            tokenManager = tokenManager,
            accessTokenApiClient = accessTokenApiClient,
            logger = logger
        )

        @Provides
        fun provideBasicAuthInterceptor() = BasicAuthInterceptor()

        @Provides
        fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.token_store), Activity.MODE_PRIVATE)
    }
}