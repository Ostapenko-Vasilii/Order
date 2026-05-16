package ru.orderdorms.core.domain.auth

interface AuthRepository {
    fun isAuthorized(): Boolean
    fun setAuthorized(value: Boolean)
}

