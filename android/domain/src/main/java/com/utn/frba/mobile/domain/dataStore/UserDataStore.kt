package com.utn.frba.mobile.domain.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.anvil.annotations.ContributesBinding
import com.utn.frba.mobile.domain.di.AppScope
import com.utn.frba.mobile.domain.models.UserModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

/**
 * Provides user information
 */
interface UserDataStore {
    suspend fun getLoggedUser(): UserModel
    suspend fun getLoggedUserOrNull(): UserModel?

    suspend fun setUser(userModel: UserModel)
}

@ContributesBinding(AppScope::class)
class UserProviderImpl @Inject constructor(
    private val context: Context
) : UserDataStore {

    private val idKey = stringPreferencesKey("user_id")

    override suspend fun getLoggedUser(): UserModel {
        val user = getLoggedUserOrNull()
        check(user != null) {
            "User not found"
        }
        return user
    }

    override suspend fun getLoggedUserOrNull(): UserModel? {
        val flow = context.dataStore.data
            .map { preferences ->
                preferences[idKey]
            }
            .filterNotNull()

        return try {
            UserModel(flow.first())
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }

    override suspend fun setUser(userModel: UserModel) {
        context.dataStore.edit { preferences ->
            preferences[idKey] = userModel.id
        }
    }
}
