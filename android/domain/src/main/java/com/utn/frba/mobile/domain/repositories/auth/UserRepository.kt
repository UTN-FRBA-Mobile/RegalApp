package com.utn.frba.mobile.domain.repositories.auth

import com.google.firebase.auth.AuthResult
import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.models.UserModel

interface UserRepository {
    suspend fun createAccount(email: String, password: String): NetworkResponse<AuthResult>
    suspend fun login(email: String, password: String): NetworkResponse<UserModel>
}
