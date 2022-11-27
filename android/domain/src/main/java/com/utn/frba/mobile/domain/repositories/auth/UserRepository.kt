package com.utn.frba.mobile.domain.repositories.auth

import com.google.firebase.auth.AuthResult
import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.models.UserModel

interface UserRepository {
    suspend fun createAccount(email: String, password: String, name: String? = null, lastName: String? = null): NetworkResponse<UserModel>
    suspend fun login(email: String, password: String): NetworkResponse<UserModel>
    suspend fun updateAccount(userId: String, values: Map<String, Any>): NetworkResponse<UserModel>
    suspend fun setDeviceToken(token: String): NetworkResponse<UserModel>
}
