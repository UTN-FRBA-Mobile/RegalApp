package com.utn.frba.mobile.regalapp.itemList

import android.content.Context
import android.content.Intent
import com.utn.frba.mobile.domain.dataStore.UserDataStore
import com.utn.frba.mobile.domain.di.qualifiers.ActivityContext
import com.utn.frba.mobile.domain.models.ItemModel
import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.repositories.events.EventsRepository
import io.github.fededri.arch.interfaces.Processor
import javax.inject.Inject

class ItemsProcessor @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val userDataStore: UserDataStore,
    @ActivityContext private val context: Context
) : Processor<ItemSideEffects, ItemsActions> {
    override suspend fun dispatchSideEffect(effect: ItemSideEffects): ItemsActions {
        return when (effect) {
            is ItemSideEffects.LoadItemsList -> loadItemsList(effect)
            is ItemSideEffects.UpdateItem -> updateItem(effect)
            is ItemSideEffects.ChangeItemStatus -> handleStatusChange(effect)
            is ItemSideEffects.ShareInvitationToEvent -> shareInvitationToEvent(effect)
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

    private suspend fun shareInvitationToEvent(
        effect: ItemSideEffects.ShareInvitationToEvent
    ): ItemsActions {
        val userModel = userDataStore.getLoggedUser()
        val uri = "regalapp://events/join/${effect.eventId}?invitedBy=${userModel.name}"
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, uri)
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(intent, null))
        return ItemsActions.NO_OP
    }

    private suspend fun handleStatusChange(
        effect: ItemSideEffects.ChangeItemStatus,
    ): ItemsActions {
        val user = userDataStore.getLoggedUser()
        val newStatus = !effect.item.status!!
        val newBoughtBy = if (newStatus) user.name else ""
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
            }

            is NetworkResponse.Error -> {
                ItemsActions.HandleError
            }
        }
}
