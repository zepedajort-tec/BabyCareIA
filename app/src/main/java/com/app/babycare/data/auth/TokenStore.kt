package com.app.babycare.data.auth

interface TokenStore {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
    fun saveUser(id: Int, name: String, email: String)
    fun getUser(): CurrentUser?
    fun clearUser()
    fun clearSession() // borra token + user
}