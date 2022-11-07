package com.utn.frba.mobile.regalapp.itemList

import io.github.fededri.arch.interfaces.StateMapper

class ItemsListStateMapper : StateMapper<ItemsState, ItemsListRenderState> {
    override fun mapToRenderState(state: ItemsState): ItemsListRenderState {
        return ItemsListRenderState(state.title, state.eventId, state.filteredItems)
    }
}
