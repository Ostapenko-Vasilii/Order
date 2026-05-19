package ru.orderdorms.core.data.auth

import com.russhwolf.settings.Settings

class SettingsTokenStorage(
    private val settings: Settings,
) : TokenStorage {
    override fun getInviteToken(): String? = settings.getStringOrNull(KEY_INVITE)

    override fun setInviteToken(value: String?) {
        settings.putOrRemove(KEY_INVITE, value)
    }

    override fun getTempToken(): String? = settings.getStringOrNull(KEY_TEMP)

    override fun setTempToken(value: String?) {
        settings.putOrRemove(KEY_TEMP, value)
    }

    override fun getFinalRegisterToken(): String? = settings.getStringOrNull(KEY_FINAL_REGISTER)

    override fun setFinalRegisterToken(value: String?) {
        settings.putOrRemove(KEY_FINAL_REGISTER, value)
    }

    override fun getResetToken(): String? = settings.getStringOrNull(KEY_RESET)

    override fun setResetToken(value: String?) {
        settings.putOrRemove(KEY_RESET, value)
    }

    override fun getAccessToken(): String? = settings.getStringOrNull(KEY_ACCESS)

    override fun setAccessToken(value: String?) {
        settings.putOrRemove(KEY_ACCESS, value)
    }

    override fun getRefreshToken(): String? = settings.getStringOrNull(KEY_REFRESH)

    override fun setRefreshToken(value: String?) {
        settings.putOrRemove(KEY_REFRESH, value)
    }

    override fun clearFlowTokens() {
        settings.remove(KEY_INVITE)
        settings.remove(KEY_TEMP)
        settings.remove(KEY_FINAL_REGISTER)
        settings.remove(KEY_RESET)
    }

    override fun clearAuthTokens() {
        settings.remove(KEY_ACCESS)
        settings.remove(KEY_REFRESH)
    }

    private fun Settings.putOrRemove(key: String, value: String?) {
        if (value.isNullOrBlank()) remove(key) else putString(key, value)
    }

    private companion object {
        private const val KEY_INVITE = "auth.invite_token"
        private const val KEY_TEMP = "auth.temp_token"
        private const val KEY_FINAL_REGISTER = "auth.final_register_token"
        private const val KEY_RESET = "auth.reset_token"
        private const val KEY_ACCESS = "auth.access_token"
        private const val KEY_REFRESH = "auth.refresh_token"
    }
}
