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
    object AddEventClicked : EventsActions()
    // endregion

    //region processor actions
    data class HandleEventsList(val events: List<EventModel>) : EventsActions()
    //endregion


}

sealed class EventSideEffects(
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    override val coroutineScope: CoroutineScope? = null
) : SideEffectInterface {
    object LoadEventsList : EventSideEffects()
}

sealed class ListEvents {
    data class OpenEventDetails(val event: EventModel) : ListEvents()
    object OpenAddEventScreen : ListEvents()
}