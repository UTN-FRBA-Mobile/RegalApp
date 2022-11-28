package com.utn.frba.mobile.domain.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.anvil.annotations.ContributesBinding
import com.utn.frba.mobile.domain.di.AppScope
import com.utn.frba.mobile.domain.di.qualifiers.AppContext
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
    suspend fun deleteUser()
}

@ContributesBinding(AppScope::class)
class UserProviderImpl @Inject constructor(
    @AppContext private val context: Context
) : UserDataStore {

    // TODO Would be better to create a scheme using Proto
    private val idKey = stringPreferencesKey("user_id")
    private val nameKey = stringPreferencesKey("user_name")
    private val lastNameKey = stringPreferencesKey("user_lastname")


    override suspend fun deleteUser() {
        context.dataStore.edit {
            it.remove(idKey)
            it.remove(nameKey)
            it.remove(lastNameKey)
        }
    }

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
                val id = preferences[idKey]
                val name = preferences[nameKey].orEmpty()
                val lastName = preferences[lastNameKey].orEmpty()
                if (id != null) {
                    UserModel(id, name, lastName)
                } else {
                    null
                }
            }
            .filterNotNull()

        return try {
            flow.first()
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }

    override suspend fun setUser(userModel: UserModel) {
        context.dataStore.edit { preferences ->
            preferences[idKey] = userModel.id
            preferences[nameKey] = userModel.name
            preferences[lastNameKey] = userModel.lastName
        }
    }
}
