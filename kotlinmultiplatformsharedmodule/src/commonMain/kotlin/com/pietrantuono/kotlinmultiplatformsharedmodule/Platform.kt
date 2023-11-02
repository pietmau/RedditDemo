package com.pietrantuono.kotlinmultiplatformsharedmodule

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform