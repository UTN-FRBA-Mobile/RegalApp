package com.utn.frba.mobile.regalapp.addItem

import com.utn.frba.mobile.domain.models.EventModel
import com.utn.frba.mobile.domain.models.ItemModel
import io.github.fededri.arch.interfaces.SideEffectInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

data class AddItemState(
    val name: String? = null,
    val quantity: String? = null,
    val price: String? = null,
    val location: String? = null,
)

sealed class AddItemActions {
    // region User initiated actions
    object FetchInitialList : AddItemActions()
    data class SetName(val name: String) : AddItemActions()
    data class SetQuantity(val quantity: String): AddItemActions()
    data class SetPrice(val price: String): AddItemActions()
    data class SetLocation(val location: String): AddItemActions()
    object SaveItemClicked : AddItemActions()
    data class HandleSaveItemSucceded(val item: ItemModel): AddItemActions()
    object CancelClicked : AddItemActions()
    // endregion
}

sealed class AddItemSideEffects(
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    override val coroutineScope: CoroutineScope? = null
) : SideEffectInterface {
    data class SaveItem(val item: ItemModel): AddItemSideEffects()
}
sealed class ListEvents {
    object OpenItemList: ListEvents()
}