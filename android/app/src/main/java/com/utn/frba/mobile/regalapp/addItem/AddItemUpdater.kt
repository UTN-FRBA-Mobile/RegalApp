package com.utn.frba.mobile.regalapp.addItem

import com.utn.frba.mobile.domain.models.ItemModel
import io.github.fededri.arch.Next
import io.github.fededri.arch.interfaces.Updater
import javax.inject.Inject

typealias NextResult = Next<AddItemState, AddItemSideEffects, ListEvents>

class AddItemUpdater @Inject constructor() :
    Updater<AddItemActions, AddItemState, AddItemSideEffects, ListEvents> {
    override fun onNewAction(
        action: AddItemActions,
        currentState: AddItemState
    ): NextResult {
        return when (action) {
            is AddItemActions.FetchInitialList -> fetchInitialList(currentState, action)
            is AddItemActions.SetName -> Next.State(currentState.copy(name = action.name))
            is AddItemActions.SetQuantity -> Next.State(currentState.copy(quantity = action.quantity))
            is AddItemActions.SetPrice -> Next.State(currentState.copy(price = action.price))
            is AddItemActions.SetLocation -> Next.State(currentState.copy(location = action.location))
            is AddItemActions.SaveItemClicked -> handleSave(currentState)
            is AddItemActions.HandleSaveItemSucceded -> handleSaveItemSucceded(currentState, action)
            is AddItemActions.CancelClicked -> handleCancel(currentState)
        }
    }

    private fun fetchInitialList(
        currentState: AddItemState,
        action: AddItemActions.FetchInitialList
    ): NextResult {
        // TODO
        return Next.State(currentState)
    }

    private fun handleSave(
        currentState: AddItemState,
    ): NextResult {

        if(
            currentState.name.isNullOrBlank()
            || currentState.quantity.isNullOrBlank()
        ) {
            return Next.State(currentState)
        }
        val item = ItemModel(
            name = currentState.name,
            quantity = currentState.quantity.toInt(),
            price = currentState.price?.toFloat(),
            location = currentState.location,
        )
        return Next.StateWithSideEffects(
            currentState,
            setOf(
                AddItemSideEffects.SaveItem(
                    item = item
                )
            )
        )

    }

    private fun handleCancel(
        currentState: AddItemState,
    ): NextResult {
        return Next.StateWithEvents(
            currentState,
            setOf(
                ListEvents.OpenItemList
            )
        )
    }

    private fun handleSaveItemSucceded(
        currentState: AddItemState,
        action: AddItemActions
    ): NextResult {
        return Next.StateWithEvents(
            currentState,
            setOf(
                ListEvents.OpenItemList
            )
        )
    }

}