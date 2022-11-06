package com.utn.frba.mobile.regalapp.itemList

import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.repositories.events.EventsRepository
import io.github.fededri.arch.interfaces.Processor
import javax.inject.Inject

class ItemsProcessor @Inject constructor(
    private val eventsRepository: EventsRepository
) : Processor<ItemSideEffects, ItemsActions> {
    override suspend fun dispatchSideEffect(effect: ItemSideEffects): ItemsActions {
        return when (effect) {
            is ItemSideEffects.LoadItemsList -> loadItemsList(effect)
        }
    }

    private suspend fun loadItemsList(effect: ItemSideEffects.LoadItemsList): ItemsActions {
        return when (val result = eventsRepository.fetchItemsList(effect.eventId)) {
            is NetworkResponse.Success -> {
                ItemsActions.HandleItemsList(result.data ?: emptyList())
            }

            is NetworkResponse.Error -> {
                ItemsActions.HandleError
            }
        }
    }
}
