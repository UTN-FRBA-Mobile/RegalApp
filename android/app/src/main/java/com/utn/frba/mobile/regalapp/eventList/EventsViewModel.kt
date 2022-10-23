package com.utn.frba.mobile.regalapp.eventList

import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.fededri.arch.ArchViewModel
import io.github.fededri.arch.ThreadInfo
import io.github.fededri.arch.coroutines.EventsConfiguration
import javax.inject.Inject

class EventsViewModel(
    processor: EventsProcessor,
    updater: EventsUpdater,
    threadInfoProvider: ThreadInfo
) : ArchViewModel<EventsActions, EventsState, EventSideEffects, ListEvents, Nothing>(
    updater,
    initialState = EventsState(),
    threadInfo = threadInfoProvider,
    processor = processor
) {

    class Factory @Inject constructor(
        private val processor: EventsProcessor,
        private val updater: EventsUpdater
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EventsViewModel(
                processor,
                updater,
                object: ThreadInfo {
                    override fun isMainThread(): Boolean {
                        return Looper.myLooper() == Looper.getMainLooper()
                    }
                }
            ) as T
        }
    }
}