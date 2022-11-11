package com.utn.frba.mobile.regalapp.addEvent

import com.utn.frba.mobile.domain.repositories.events.EventsRepository
import io.github.fededri.arch.interfaces.Processor
import javax.inject.Inject

class AddEventProcessor @Inject constructor(
    private val eventsRepository: EventsRepository
) : Processor<AddEventSideEffects, AddEventActions> {
    override suspend fun dispatchSideEffect(effect: AddEventSideEffects): AddEventActions {
        return when (effect) {
            is AddEventSideEffects.SaveEvent -> saveEvent(effect)
        }
    }

    private suspend fun saveEvent(effect: AddEventSideEffects.SaveEvent): AddEventActions {
        val eventMap = mapOf(
            "name" to effect.event.name,
            "date" to effect.event.date,
            "image" to effect.event.image,
            "ownerId" to effect.event.ownerId
        )
        eventsRepository.createEvent(eventMap)
        return AddEventActions.HandleCreateEventSuccess(effect.event)
    }
}
