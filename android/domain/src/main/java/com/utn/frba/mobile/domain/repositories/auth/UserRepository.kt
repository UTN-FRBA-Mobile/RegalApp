package com.utn.frba.mobile.domain.repositories.auth

import com.google.firebase.auth.AuthResult
import com.utn.frba.mobile.domain.models.NetworkResponse

interface UserRepository {
    suspend fun createAccount(email: String, password: String): NetworkResponse<AuthResult>
}
