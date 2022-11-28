package com.utn.frba.mobile.regalapp.eventList

import com.utn.frba.mobile.domain.repositories.auth.UserRepository
import com.utn.frba.mobile.domain.repositories.events.EventsRepository
import io.github.fededri.arch.interfaces.Processor
import javax.inject.Inject

class EventsProcessor @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val userRepository: UserRepository,
) : Processor<EventSideEffects, EventsActions> {
    override suspend fun dispatchSideEffect(effect: EventSideEffects): EventsActions {
        return when (effect) {
            is EventSideEffects.LoadEventsList -> loadEventsList(effect)
            is EventSideEffects.SetDeviceToken -> setDeviceToken(effect)
        }
    }

    private suspend fun loadEventsList(effect: EventSideEffects.LoadEventsList): EventsActions {
        val events = eventsRepository.fetchUserEvents()

        return EventsActions.HandleEventsList(events.data ?: emptyList())
    }

    private suspend fun setDeviceToken(
        effect: EventSideEffects.SetDeviceToken
    ): EventsActions {
        userRepository.setDeviceToken(effect.token)
        return EventsActions.FetchInitialList
    }
}
