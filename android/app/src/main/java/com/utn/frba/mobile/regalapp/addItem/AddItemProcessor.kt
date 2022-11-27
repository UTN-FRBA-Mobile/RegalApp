package com.utn.frba.mobile.regalapp.addItem

import com.utn.frba.mobile.domain.repositories.events.EventsRepository
import io.github.fededri.arch.interfaces.Processor
import javax.inject.Inject

class AddItemProcessor @Inject constructor(
    private val eventsRepository: EventsRepository
) : Processor<AddItemSideEffects, AddItemActions> {
    override suspend fun dispatchSideEffect(effect: AddItemSideEffects): AddItemActions {
        return when (effect) {
            is AddItemSideEffects.SaveItem -> saveItem(effect)
        }
    }

    private suspend fun saveItem(effect: AddItemSideEffects.SaveItem): AddItemActions {
        eventsRepository.addItems(effect.eventId, listOf(effect.item))
        return AddItemActions.HandleSaveItemSucceded(
            effect.item
        )
    }
}
