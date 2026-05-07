package ru.orderdorms

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform