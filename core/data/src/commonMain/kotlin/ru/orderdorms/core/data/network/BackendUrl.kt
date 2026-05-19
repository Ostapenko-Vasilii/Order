package ru.orderdorms.core.data.network

import ru.orderdorms.core.data.BuildKonfig

object BackendUrlProvider {
    fun resolve(): String {
        return BuildKonfig.BASE_URL.trimEnd('/')
    }
}


