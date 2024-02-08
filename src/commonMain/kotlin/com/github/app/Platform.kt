package com.github.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
