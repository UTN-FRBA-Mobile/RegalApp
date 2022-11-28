package com.utn.frba.mobile.domain.repositories.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.anvil.annotations.ContributesBinding
import com.utn.frba.mobile.domain.DBCollections
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
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : UserRepository {
    override suspend fun createAccount(
        email: String,
        password: String,
        name: String?,
        lastName: String?
    ) =
        withContext(Dispatchers.IO) {
            safeCall {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val userId = result.user!!.uid
                val userValues = mutableMapOf<String, Any>()

                if (name != null) {
                    userValues["name"] = name
                }

                if (lastName != null) {
                    userValues["lastName"] = lastName
                }

                userValues["email"] = email

                val userModel = UserModel(
                    userId,
                    email,
                    name.orEmpty(),
                    lastName.orEmpty()
                )

                createUserDocument(userId, userValues)
                NetworkResponse.Success(userModel)
            }
        }

    override suspend fun login(email: String, password: String): NetworkResponse<UserModel> =
        safeCall {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid

            return if (userId != null) {
                val userModel = getUserModel(userId)

                NetworkResponse.Success(userModel)
            } else {
                NetworkResponse.Error("User id is invalid")
            }
        }

    private suspend fun updateUserFieldValues(
        userId: String,
        values: Map<String, Any>
    ) {
        val userRef = db.collection(DBCollections.USERS.value).document(userId)
        userRef.update(
            values
        ).await()
    }

    private suspend fun createUserDocument(userId: String, fieldValues: Map<String, Any>) {
        val usersCollection = db.collection(DBCollections.USERS.value)
        usersCollection.document(userId)
            .set(fieldValues)
            .await()
    }

    private suspend fun getUserModel(userId: String): UserModel {
        val userSnapshot = db.collection(DBCollections.USERS.value).document(userId).get().await()
        val name = userSnapshot.get("name") as? String
        val lastName = userSnapshot.get("lastName") as? String
        val email = userSnapshot.get("email") as String
        return UserModel(userId, email.orEmpty(), name.orEmpty(), lastName.orEmpty())
    }
}
