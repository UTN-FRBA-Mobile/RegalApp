package com.utn.frba.mobile.regalapp.joinEvent

import com.utn.frba.mobile.domain.models.EventModel
import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.repositories.events.EventsRepository
import io.github.fededri.arch.Next
import io.github.fededri.arch.interfaces.Processor
import io.github.fededri.arch.interfaces.SideEffectInterface
import io.github.fededri.arch.interfaces.Updater
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

sealed class JoinEventState {
    object Loading : JoinEventState()

    data class EventLoaded(
        val event: EventModel
    ) : JoinEventState()

    object Error : JoinEventState()
}

sealed class JoinEventActions {
    data class LoadEventDetails(val eventId: String) : JoinEventActions()

    data class SetEvent(val event: EventModel) : JoinEventActions()

    object Error : JoinEventActions()

    object JoinEvent : JoinEventActions()
    data class SuccessfullyJoinedEvent(val eventId: String) : JoinEventActions()
}

sealed class JoinEventSideEffects(
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    override val coroutineScope: CoroutineScope? = null
) : SideEffectInterface {
    data class FetchEvent(val eventId: String) : JoinEventSideEffects()
    data class JoinEvent(val eventId: String) : JoinEventSideEffects()
}

sealed class JoinEventEvents {
    data class NavigateToEvent(val eventId: String) : JoinEventEvents()
}

class JoinEventUpdater @Inject constructor() :
    Updater<JoinEventActions, JoinEventState, JoinEventSideEffects, JoinEventEvents> {
    override fun onNewAction(
        action: JoinEventActions,
        currentState: JoinEventState
    ): Next<JoinEventState, JoinEventSideEffects, JoinEventEvents> {
        return when (action) {
            is JoinEventActions.LoadEventDetails -> Next.StateWithSideEffects(
                currentState,
                setOf(JoinEventSideEffects.FetchEvent(action.eventId))
            )
            is JoinEventActions.SetEvent -> Next.State(JoinEventState.EventLoaded(action.event))
            is JoinEventActions.Error -> Next.State(JoinEventState.Error)
            is JoinEventActions.JoinEvent -> handleJoinEvent(currentState, action)
            is JoinEventActions.SuccessfullyJoinedEvent -> Next.StateWithEvents(currentState, setOf(JoinEventEvents.NavigateToEvent(action.eventId)))
        }
    }

    private fun handleJoinEvent(
        currentState: JoinEventState,
        action: JoinEventActions.JoinEvent
    ): Next<JoinEventState, JoinEventSideEffects, JoinEventEvents> {
        return if (currentState is JoinEventState.EventLoaded) {
            Next.StateWithSideEffects(
                currentState,
                setOf(JoinEventSideEffects.JoinEvent(currentState.event.id))
            )
        } else {
            Next.State(currentState)
        }
    }
}

class JoinEventProcessor @Inject constructor(
    private val eventsRepository: EventsRepository
) : Processor<JoinEventSideEffects, JoinEventActions> {

    override suspend fun dispatchSideEffect(effect: JoinEventSideEffects): JoinEventActions {
        return when (effect) {
            is JoinEventSideEffects.FetchEvent -> fetchEventDetails(effect.eventId)
            is JoinEventSideEffects.JoinEvent -> joinEvent(effect.eventId)
        }
    }

    private suspend fun joinEvent(eventId: String): JoinEventActions {


        return when (val result = eventsRepository.joinEvent(eventId)) {
            is NetworkResponse.Success -> {
                result.data?.let { JoinEventActions.SuccessfullyJoinedEvent(eventId) }
                    ?: JoinEventActions.Error
            }

            is NetworkResponse.Error -> {
                JoinEventActions.Error
            }
        }
    }

    private suspend fun fetchEventDetails(eventId: String): JoinEventActions {
        return when (val result = eventsRepository.fetchEvent(eventId)) {
            is NetworkResponse.Success -> {
                result.data?.let { JoinEventActions.SetEvent(it) }
                    ?: JoinEventActions.Error
            }

            is NetworkResponse.Error -> {
                JoinEventActions.Error
            }
        }
    }
}