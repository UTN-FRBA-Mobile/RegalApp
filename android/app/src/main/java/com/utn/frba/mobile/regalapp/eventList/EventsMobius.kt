package com.utn.frba.mobile.regalapp.eventList

import com.utn.frba.mobile.domain.models.EventModel
import io.github.fededri.arch.interfaces.SideEffectInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

data class EventsState(
    val events: List<EventModel> = emptyList(),
    val selectedEvent: EventModel? = null,
    val loading: Boolean = false
)

sealed class EventsActions {
    // region User initiated actions
    object FetchInitialList : EventsActions()
    data class OpenEventDetails(val event: EventModel) : EventsActions()
    data class OpenItemsList(val event: EventModel) : EventsActions()
    object AddEventClicked: EventsActions() // 2
    object ProfileClicked: EventsActions()
    // endregion

    //region processor actions
    data class HandleEventsList(val events: List<EventModel>) : EventsActions()
    data class SetDeviceToken(val token: String): EventsActions()
    //endregion
}

sealed class EventSideEffects(
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    override val coroutineScope: CoroutineScope? = null
) : SideEffectInterface {
    object LoadEventsList : EventSideEffects()
    data class SetDeviceToken(val token: String): EventSideEffects()
}

sealed class ListEvents {
    data class OpenEventDetails(val event: EventModel) : ListEvents()
    data class OpenItemsList(val event: EventModel) : ListEvents()
    object OpenAddEventScreen: ListEvents() // 4
    object OpenProfileScreen: ListEvents()
}
