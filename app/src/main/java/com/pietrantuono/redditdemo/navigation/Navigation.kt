package com.pietrantuono.redditdemo.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.pietrantuono.redditdemo.navigation.Destination.Detail

internal const val POSTS = "posts"
internal const val ID = "id"
internal const val DETAIL = "detail"

internal fun NavHostController.navigateTo(destination: Destination) {
    when (destination) {
        is Detail -> navigate("$DETAIL/${destination.name}")
    }
}

sealed class Destination {
    data class Detail(val name: String) : Destination()
}

internal val detailNavArguments
    get() = listOf(navArgument(ID) { type = NavType.StringType })