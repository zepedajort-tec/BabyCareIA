package com.app.babycare.data.auth

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptedTokenStore @Inject constructor(
    private val context: Context
) : TokenStore {

    private val prefsName = "secure_prefs"
    private val keyToken = "access_token"

    private val keyUserId = "current_user_id"
    private val keyUserName = "current_user_name"
    private val keyUserEmail = "current_user_email"

    private val prefs by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            prefsName,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun saveToken(token: String) {
        prefs.edit().putString(keyToken, token).apply()
    }

    override fun getToken(): String? {
        return prefs.getString(keyToken, null)
    }

    override fun clearToken() {
        prefs.edit().remove(keyToken).apply()
    }

    override fun saveUser(id: Int, name: String, email: String) {
        prefs.edit()
            .putInt(keyUserId, id)
            .putString(keyUserName, name)
            .putString(keyUserEmail, email)
            .apply()
    }

    override fun getUser(): CurrentUser? {
        // Si no existe id retornamos null
        if (!prefs.contains(keyUserId)) return null
        val id = prefs.getInt(keyUserId, -1)
        val name = prefs.getString(keyUserName, null)
        val email = prefs.getString(keyUserEmail, null)
        return if (id != -1 && !name.isNullOrEmpty() && !email.isNullOrEmpty()) {
            CurrentUser(id = id, name = name, email = email)
        } else {
            null
        }
    }

    override fun clearUser() {
        prefs.edit()
            .remove(keyUserId)
            .remove(keyUserName)
            .remove(keyUserEmail)
            .apply()
    }

    override fun clearSession() {
        clearToken()
        clearUser()
    }
}