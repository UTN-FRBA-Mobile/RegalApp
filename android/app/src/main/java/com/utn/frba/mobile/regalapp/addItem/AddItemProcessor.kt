package com.utn.frba.mobile.regalapp.addItem

import com.utn.frba.mobile.domain.models.EventModel
import io.github.fededri.arch.interfaces.Processor
import javax.inject.Inject

class AddItemProcessor @Inject constructor() : Processor<AddItemSideEffects, AddItemActions> {
    override suspend fun dispatchSideEffect(effect: AddItemSideEffects): AddItemActions {
        return when (effect) {
            is AddItemSideEffects.SaveItem -> saveItem(effect)
        }
    }


    private suspend fun saveItem(effect: AddItemSideEffects.SaveItem): AddItemActions {
        return AddItemActions.HandleSaveItemSucceded(
            effect.item
        )
    }
}