package ru.orderdorms

fun greetingMessage(platformName: String): String = "Hello, $platformName!"

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return greetingMessage(platform.name)
    }
}