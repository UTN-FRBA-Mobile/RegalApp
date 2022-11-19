package com.utn.frba.mobile.regalapp.login

import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utn.frba.mobile.domain.dataStore.UserDataStore
import com.utn.frba.mobile.domain.models.EventModel
import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.models.UserModel
import com.utn.frba.mobile.domain.repositories.auth.UserRepository
import com.utn.frba.mobile.regalapp.eventList.EventsActions
import com.utn.frba.mobile.regalapp.eventList.EventsState
import com.utn.frba.mobile.regalapp.eventList.ListEvents
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

typealias NextResult = Next<AuthenticationState, AuthenticationEffects, AuthenticationEvents>

data class AuthenticationState(
    val email: String? = null,
    val password: String? = null,
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val selectedEvent: EventModel? = null
)

sealed class AuthenticationEvents() {
    object LoginFailed : AuthenticationEvents()
    object MissingFields : AuthenticationEvents()
}

sealed class AuthenticationActions {
    data class SetEmail(val email: String) : AuthenticationActions()
    data class SetPassword(val password: String) : AuthenticationActions()
    object LoginClicked : AuthenticationActions()

    data class HandleLoginSucceeded(val user: UserModel) : AuthenticationActions()
    object InvalidCredentials : AuthenticationActions()
    object SkipLogin : AuthenticationActions()
    object NO_OP : AuthenticationActions()
}

sealed class AuthenticationEffects(
    override val coroutineScope: CoroutineScope? = null,
    override val dispatcher: CoroutineDispatcher? = Dispatchers.IO
) : SideEffectInterface {
    data class Login(val username: String, val password: String) : AuthenticationEffects()
    object CheckIsAlreadyLoggedIn : AuthenticationEffects()
}

class AuthenticationUpdater @Inject constructor() :
    Updater<AuthenticationActions, AuthenticationState, AuthenticationEffects, AuthenticationEvents> {
    override fun onNewAction(
        action: AuthenticationActions,
        currentState: AuthenticationState
    ): Next<AuthenticationState, AuthenticationEffects, AuthenticationEvents> {
        return when (action) {
            is AuthenticationActions.LoginClicked -> handleLoginClicked(currentState)
            is AuthenticationActions.SetEmail -> Next.State(currentState.copy(email = action.email))
            is AuthenticationActions.SetPassword -> Next.State(currentState.copy(password = action.password))
            is AuthenticationActions.HandleLoginSucceeded -> handleLoginSucceeded(
                currentState,
                action
            )
            is AuthenticationActions.InvalidCredentials -> handleInvalidCredentials(currentState)
            is AuthenticationActions.SkipLogin -> handleUserAlreadyLogged(currentState)
            is AuthenticationActions.NO_OP -> Next.State(currentState)
        }
    }




    private fun handleUserAlreadyLogged(currentState: AuthenticationState): NextResult {
        return Next.State(currentState.copy(isLoggedIn = true))
    }

    private fun handleInvalidCredentials(currentState: AuthenticationState): NextResult {
        return Next.StateWithEvents(
            currentState.copy(isLoading = false),
            setOf(AuthenticationEvents.LoginFailed)
        )
    }

    private fun handleLoginSucceeded(
        currentState: AuthenticationState,
        action: AuthenticationActions.HandleLoginSucceeded
    ): NextResult {
        return Next.State(currentState.copy(isLoggedIn = true, isLoading = false))
    }

    private fun handleLoginClicked(currentState: AuthenticationState): NextResult {
        return if (currentState.email != null && currentState.password != null) {
            Next.StateWithSideEffects(
                currentState.copy(isLoading = true),
                setOf(
                    AuthenticationEffects.Login(
                        currentState.email,
                        currentState.password
                    )
                )
            )
        } else {
            Next.StateWithEvents(currentState, setOf(AuthenticationEvents.MissingFields))
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
            is AuthenticationEffects.CheckIsAlreadyLoggedIn -> handleCheckAlreadyLoggedIn()
        }
    }

    private suspend fun handleCheckAlreadyLoggedIn(): AuthenticationActions {
        val user = userDataStore.getLoggedUser()
        return if (user != null) {
            AuthenticationActions.SkipLogin
        } else {
            AuthenticationActions.NO_OP
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
) : ArchViewModel<AuthenticationActions, AuthenticationState, AuthenticationEffects, AuthenticationEvents, Nothing>(
    updater = updater,
    initialState = AuthenticationState(),
    threadInfo = threadInfo,
    processor = processor,
    initialEffects = setOf(AuthenticationEffects.CheckIsAlreadyLoggedIn)
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