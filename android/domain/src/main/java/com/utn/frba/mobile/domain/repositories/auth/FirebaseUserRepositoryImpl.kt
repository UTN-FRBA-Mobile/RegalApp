package com.utn.frba.mobile.domain.repositories.auth

import com.google.firebase.auth.FirebaseAuth
import com.squareup.anvil.annotations.ContributesBinding
import com.utn.frba.mobile.domain.di.AppScope
import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.models.UserModel
import com.utn.frba.mobile.domain.utils.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ContributesBinding(AppScope::class)
@AppScope
class FirebaseUserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : UserRepository {
    override suspend fun createAccount(email: String, password: String) =
        withContext(Dispatchers.IO) {
            safeCall {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                NetworkResponse.Success(result)
            }
        }

    override suspend fun login(email: String, password: String): NetworkResponse<UserModel> =
        safeCall {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid
            return if (userId != null) {
                NetworkResponse.Success(UserModel(userId))
            } else {
                NetworkResponse.Error("User id is invalid")
            }
        }
}
