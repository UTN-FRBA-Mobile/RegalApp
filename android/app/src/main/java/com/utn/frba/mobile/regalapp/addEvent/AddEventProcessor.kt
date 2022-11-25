package com.utn.frba.mobile.regalapp.addEvent

import com.utn.frba.mobile.domain.dataStore.UserDataStore
import com.utn.frba.mobile.domain.models.EventFields
import com.utn.frba.mobile.domain.models.ItemModel
import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.repositories.events.EventsRepository
import io.github.fededri.arch.interfaces.Processor
import javax.inject.Inject

class AddEventProcessor @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val userDataStore: UserDataStore
) : Processor<AddEventSideEffects, AddEventActions> {
    override suspend fun dispatchSideEffect(effect: AddEventSideEffects): AddEventActions {
        return when (effect) {
            is AddEventSideEffects.CreateEvent -> createEvent(effect)
        }
    }

    private suspend fun createEvent(effect: AddEventSideEffects.CreateEvent): AddEventActions {
        val user = userDataStore.getLoggedUser()?.id
        user.let {
            val eventMap = mapOf(
                "name" to effect.event.name,
                "date" to effect.event.date,
                EventFields.OWNER_ID.value to user,
                "items" to emptyList<List<ItemModel>>()
            )
            return when (eventsRepository.createEvent(eventMap as Map<String, Any>)) {
                is NetworkResponse.Success -> {
                    AddEventActions.HandleCreateEventSuccess
                }
                is NetworkResponse.Error -> {
                    AddEventActions.HandleCreateEventFailure
                }
            }
        }
    }
}
