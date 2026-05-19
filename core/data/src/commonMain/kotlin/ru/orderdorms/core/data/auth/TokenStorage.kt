package ru.orderdorms.core.data.auth

interface TokenStorage {
    fun getInviteToken(): String?
    fun setInviteToken(value: String?)

    fun getTempToken(): String?
    fun setTempToken(value: String?)

    fun getFinalRegisterToken(): String?
    fun setFinalRegisterToken(value: String?)

    fun getResetToken(): String?
    fun setResetToken(value: String?)

    fun getAccessToken(): String?
    fun setAccessToken(value: String?)

    fun getRefreshToken(): String?
    fun setRefreshToken(value: String?)

    fun clearFlowTokens()
    fun clearAuthTokens()
}
