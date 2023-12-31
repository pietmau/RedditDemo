package com.pietrantuono.network

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import com.pietrantuono.network.api.accesstoken.AccessTokenApiClientImpl
import com.pietrantuono.network.api.accesstoken.AccessTokenApiClient
import com.pietrantuono.network.interceptor.BearerTokenAuthInterceptor
import com.pietrantuono.network.networkchecker.NetworkChecker
import com.pietrantuono.network.networkchecker.NetworkCheckerImpl
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

    @Binds
    fun bindNetworkChecker(networkCheckerImpl: NetworkCheckerImpl): NetworkChecker

    companion object {

        @Provides
        fun bindAccessTokenApiClient(): AccessTokenApiClient = AccessTokenApiClientImpl(
            clientId = BuildConfig.CLIENT_ID
        )

        @Provides
        fun provideConnectivityManager(@ApplicationContext context: Context) =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        @Provides
        fun provideBearerTokenInterceptor(tokenManager: TokenManager) = BearerTokenAuthInterceptor(
            tokenManager = tokenManager
        )

        @Provides
        fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences = context.getSharedPreferences(
            context.getString(R.string.token_store),
            Activity.MODE_PRIVATE
        )
    }
}
