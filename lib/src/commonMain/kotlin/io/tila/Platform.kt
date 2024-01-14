package io.tila

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform