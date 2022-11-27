package com.utn.frba.mobile.regalapp.itemList

import com.utn.frba.mobile.domain.models.ItemModel
import io.github.fededri.arch.interfaces.SideEffectInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

data class ItemsState(
    val title: String = "",
    val eventId: String = "",
    val items: List<ItemModel> = emptyList(),
    val filteredItems: List<ItemModel> = emptyList(),
    val selectedItem: ItemModel? = null,
    val editingItem: ItemModel? = null,
    val isLoading: Boolean = false,
)

data class ItemsListRenderState(
    val title: String = "",
    val eventId: String = "",
    val items: List<ItemModel> = emptyList()
)

sealed class ItemsActions {
    data class SetInitialArguments(val eventId: String, val title: String) : ItemsActions()

    // region User initiated actions
    object FetchInitialList : ItemsActions()
    object OpenEventDetails : ItemsActions()
    data class OpenItemDetails(val item: ItemModel) : ItemsActions()
    object AddItemClicked : ItemsActions()
    object GoBack : ItemsActions()
    data class FilterItems(val query: String) : ItemsActions()

    // item detail actions
    object CloseItemDetail : ItemsActions()
    object OpenEditItem : ItemsActions()
    data class ChangeItemStatus(val item: ItemModel) : ItemsActions()

    // item edit actions
    data class SetName(val name: String) : ItemsActions()
    data class SetQuantity(val quantity: String) : ItemsActions()
    data class SetPrice(val price: Double) : ItemsActions()
    data class SetLocation(val location: String) : ItemsActions()
    data class SetCoordinates(val latitude: Double, val longitude: Double) : ItemsActions()
    data class UpdateItemClicked(val item: ItemModel) : ItemsActions()
    data class HandleUpdateSucceeded(val item: ItemModel, val itemList: List<ItemModel>): ItemsActions()
    data class CloseEditItem(val item: ItemModel? = null) : ItemsActions()


    // endregion

    //region processor actions
    data class HandleItemsList(val items: List<ItemModel>) : ItemsActions()

    object HandleError : ItemsActions()
    object ShareInvitationToEvent : ItemsActions()
    object NO_OP: ItemsActions()
    //endregion
}

sealed class ItemSideEffects(
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    override val coroutineScope: CoroutineScope? = null
) : SideEffectInterface {
    data class LoadItemsList(val eventId: String) : ItemSideEffects()
    data class UpdateItem(
        val eventId: String,
        val itemId: String,
        val item: ItemModel,
    ) : ItemSideEffects()

    data class ChangeItemStatus(
        val eventId: String,
        val itemId: String,
        val item: ItemModel,
    ) : ItemSideEffects()

    data class ShareInvitationToEvent(
        val eventId: String
    ): ItemSideEffects()
}

sealed class ListEvents {
    data class OpenEventDetails(val eventId: String) : ListEvents()
    data class OpenItemDetails(val item: ItemModel) : ListEvents()
    object OpenEditItemScreen : ListEvents()
    object OpenAddItemScreen : ListEvents()
    object BackButtonPressed : ListEvents()
    object CloseDetailPressed : ListEvents()
    object CloseEditPressed : ListEvents()
}
