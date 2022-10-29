package com.utn.frba.mobile.regalapp.itemList

import com.utn.frba.mobile.domain.models.EventModel
import io.github.fededri.arch.interfaces.Processor
import javax.inject.Inject

class ItemsProcessor @Inject constructor() : Processor<ItemSideEffects, ItemsActions> {
    override suspend fun dispatchSideEffect(effect: ItemSideEffects): ItemsActions {
        return when (effect) {
            is ItemSideEffects.LoadItemList -> loadEventsList(effect)
        }
    }

    private suspend fun loadEventsList(effect: ItemSideEffects.LoadItemList): ItemsActions {
        // TODO
        return ItemsActions.HandleItemsList(EventModel(
            id = "",
            name = "Asado",
            ownerId = "",
        ))
    }
}