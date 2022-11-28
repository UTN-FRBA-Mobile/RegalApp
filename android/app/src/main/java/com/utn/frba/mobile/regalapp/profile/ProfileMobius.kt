package com.utn.frba.mobile.regalapp.map

import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utn.frba.mobile.domain.dataStore.UserDataStore
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

typealias NextResult = Next<ProfileState, ProfileEffects, ProfileEvents>

data class ProfileState(
    val user: UserModel? = null
)

sealed class ProfileEvents() {
    object GoBack: ProfileEvents()
}

sealed class ProfileActions {
    object FetchUser : ProfileActions()
    data class SetUser(val user: UserModel) : ProfileActions()
    object GoBack : ProfileActions()
    object NO_OP : ProfileActions()
}

sealed class ProfileEffects(
    override val coroutineScope: CoroutineScope? = null,
    override val dispatcher: CoroutineDispatcher? = Dispatchers.IO
) : SideEffectInterface {
    object SetUser: ProfileEffects()
}

class ProfileUpdater @Inject constructor(
) :
    Updater<ProfileActions, ProfileState, ProfileEffects, ProfileEvents> {
    override fun onNewAction(
        action: ProfileActions,
        currentState: ProfileState,
    ): Next<ProfileState, ProfileEffects, ProfileEvents> {
        return when (action) {
            is ProfileActions.FetchUser -> {
                handleFetchUser(currentState)
            }
            is ProfileActions.SetUser -> {
                handleSetUser(currentState, action)
            }
            is ProfileActions.GoBack -> {
                Next.StateWithEvents(
                    currentState,
                    setOf(ProfileEvents.GoBack)
                )
            }
            is ProfileActions.NO_OP -> {
                Next.State(currentState)
            }
            else -> Next.State(currentState)
        }
    }
    private fun handleFetchUser(
        currentState: ProfileState
    ): NextResult {
        return Next.StateWithSideEffects(
            currentState,
            setOf(
                ProfileEffects.SetUser
            )
        )
    }
    private fun handleSetUser(
        currentState: ProfileState,
        action: ProfileActions.SetUser
    ): NextResult {
        return Next.State(
            currentState.copy(
                user = action.user
            )
        )
    }
}

class ProfileProcessor @Inject constructor(
    private val userRepository: UserRepository,
    private val userDataStore: UserDataStore
) :
    Processor<ProfileEffects, ProfileActions> {
    override suspend fun dispatchSideEffect(effect: ProfileEffects): ProfileActions {
        return when(effect) {
            is ProfileEffects.SetUser -> handleSetUser()
            else -> ProfileActions.NO_OP
        }
    }

    private suspend fun handleSetUser(): ProfileActions {
        val user = userDataStore.getLoggedUser()
        return ProfileActions.SetUser(user)
    }
}

class ProfileViewModel(
    updater: ProfileUpdater,
    processor: ProfileProcessor,
    threadInfo: ThreadInfo
) : ArchViewModel<ProfileActions, ProfileState, ProfileEffects, ProfileEvents, Nothing>(
    updater = updater,
    initialState = ProfileState(),
    threadInfo = threadInfo,
    processor = processor,
) {

    class Factory @Inject constructor(
        private val updater: ProfileUpdater,
        private val processor: ProfileProcessor
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(
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