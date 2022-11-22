package com.utn.frba.mobile.regalapp.itemList

import com.utn.frba.mobile.domain.dataStore.UserDataStore
import com.utn.frba.mobile.domain.models.ItemModel
import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.repositories.events.EventsRepository
import io.github.fededri.arch.interfaces.Processor
import javax.inject.Inject

class ItemsProcessor @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val userDataStore: UserDataStore,
) : Processor<ItemSideEffects, ItemsActions> {
    override suspend fun dispatchSideEffect(effect: ItemSideEffects): ItemsActions {
        return when (effect) {
            is ItemSideEffects.LoadItemsList -> loadItemsList(effect)
            is ItemSideEffects.UpdateItem -> updateItem(effect)
            is ItemSideEffects.ChangeItemStatus -> handleStatusChange(effect)
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

    private suspend fun handleStatusChange(
        effect: ItemSideEffects.ChangeItemStatus,
    ): ItemsActions {
        val user = userDataStore.getLoggedUser()
        val newStatus = !effect.item.status!!
        // TODO  Add name from logged user
        val newBoughtBy = if (newStatus) user.id else ""
        return  handleEditItem(
            eventId = effect.eventId,
            itemId = effect.itemId,
            item = effect.item.copy(
                status = newStatus,
                boughtBy = newBoughtBy
            ),
        )

    }

    private suspend fun updateItem(
        effect: ItemSideEffects.UpdateItem
    ): ItemsActions {
        return handleEditItem(
            eventId = effect.eventId,
            itemId = effect.itemId,
            item = effect.item,
        )
    }

    private suspend fun handleEditItem(
        eventId: String,
        itemId: String,
        item: ItemModel,
    ) =
        when (val result = eventsRepository.editItem(
            eventId,
            itemId,
            item
        )) {
            is NetworkResponse.Success -> {
                ItemsActions.HandleItemsList(result.data?.items ?: emptyList())
                ItemsActions.CloseEditItem(item)
            }

            is NetworkResponse.Error -> {
                ItemsActions.HandleError
            }
        }
}
