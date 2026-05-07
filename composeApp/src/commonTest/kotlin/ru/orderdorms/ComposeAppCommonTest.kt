package ru.orderdorms

import kotlin.test.Test
import kotlin.test.assertEquals

class ComposeAppCommonTest {

    @Test
    fun greetingMessageFormatsPlatformName() {
        assertEquals("Hello, Android 15!", greetingMessage("Android 15"))
    }
}