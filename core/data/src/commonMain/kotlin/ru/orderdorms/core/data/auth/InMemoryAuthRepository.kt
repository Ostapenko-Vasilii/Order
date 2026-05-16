package ru.orderdorms.core.data.auth

class InMemoryAuthRepository {
    private var authorized: Boolean = false

    fun isAuthorized(): Boolean = authorized

    fun setAuthorized(value: Boolean) {
        authorized = value
    }
}


