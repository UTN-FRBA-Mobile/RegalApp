package com.utn.frba.mobile.regalapp.login

import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utn.frba.mobile.domain.dataStore.UserDataStore
import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.models.UserModel
import com.utn.frba.mobile.domain.repositories.auth.UserRepository
import io.github.fededri.arch.ArchViewModel
import io.github.fededri.arch.Next
import io.github.fededri.arch.ThreadInfo
import io.github.fededri.arch.interfaces.Processor
import io.github.fededri.arch.interfaces.SideEffectInterface
import io.github.fededri.arch.interfaces.Updater
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

typealias NextResult = Next<AuthenticationState, AuthenticationEffects, Nothing>

data class AuthenticationState(
    val email: String? = null,
    val password: String? = null,
    val isLoggedIn: Boolean = true
)

sealed class AuthenticationActions {
    data class SetEmail(val email: String) : AuthenticationActions()
    data class SetPassword(val password: String) : AuthenticationActions()
    object LoginClicked : AuthenticationActions()

    data class HandleLoginSucceeded(val user: UserModel) : AuthenticationActions()
    object InvalidCredentials : AuthenticationActions()
}

sealed class AuthenticationEffects(
    override val coroutineScope: CoroutineScope? = null,
    override val dispatcher: CoroutineDispatcher? = Dispatchers.IO
) : SideEffectInterface {
    data class Login(val username: String, val password: String) : AuthenticationEffects()
}

class AuthenticationUpdater @Inject constructor() :
    Updater<AuthenticationActions, AuthenticationState, AuthenticationEffects, Nothing> {
    override fun onNewAction(
        action: AuthenticationActions,
        currentState: AuthenticationState
    ): Next<AuthenticationState, AuthenticationEffects, Nothing> {
        return when (action) {
            is AuthenticationActions.LoginClicked -> handleLoginClicked(currentState)
            is AuthenticationActions.SetEmail -> Next.State(currentState.copy(email = action.email))
            is AuthenticationActions.SetPassword -> Next.State(currentState.copy(password = action.password))
            is AuthenticationActions.HandleLoginSucceeded -> handleLoginSucceeded(currentState, action)
            else -> {
                Next.State(currentState)
            } // TODO
        }
    }

    private fun handleLoginSucceeded(
        currentState: AuthenticationState,
        action: AuthenticationActions.HandleLoginSucceeded
    ): NextResult {
        return Next.State(currentState.copy(isLoggedIn = true))
    }

    private fun handleLoginClicked(currentState: AuthenticationState): NextResult {
        return if (currentState.email != null && currentState.password != null) {
            Next.StateWithSideEffects(
                currentState,
                setOf(
                    AuthenticationEffects.Login(
                        currentState.email,
                        currentState.password
                    )
                )
            )

        } else {
            Next.State(currentState)
        }
    }

}

class AuthenticationProcessor @Inject constructor(
    private val userRepository: UserRepository,
    private val userDataStore: UserDataStore
) :
    Processor<AuthenticationEffects, AuthenticationActions> {
    override suspend fun dispatchSideEffect(effect: AuthenticationEffects): AuthenticationActions {
        return when (effect) {
            is AuthenticationEffects.Login -> handleLogin(effect)
        }
    }

    private suspend fun handleLogin(effect: AuthenticationEffects.Login): AuthenticationActions {
        return when (val result = userRepository.login(effect.username, effect.password)) {
            is NetworkResponse.Success -> {
                val user = result.data!!
                userDataStore.setUser(user)
                AuthenticationActions.HandleLoginSucceeded(user)
            }
            is NetworkResponse.Error -> {
                AuthenticationActions.InvalidCredentials
            }
        }
    }
}


class AuthenticationViewModel(
    updater: AuthenticationUpdater,
    processor: AuthenticationProcessor,
    threadInfo: ThreadInfo
) : ArchViewModel<AuthenticationActions, AuthenticationState, AuthenticationEffects, Nothing, Nothing>(
    updater = updater,
    initialState = AuthenticationState(),
    threadInfo = threadInfo,
    processor = processor
) {

    class Factory @Inject constructor(
        private val updater: AuthenticationUpdater,
        private val processor: AuthenticationProcessor
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AuthenticationViewModel(
                updater,
                processor,
                object : ThreadInfo {
                    override fun isMainThread(): Boolean {
                        return Looper.myLooper() == Looper.getMainLooper()
                    }
                }
            ) as T
        }
    }
}