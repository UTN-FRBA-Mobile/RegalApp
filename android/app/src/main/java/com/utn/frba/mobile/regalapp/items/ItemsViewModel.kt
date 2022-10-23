package com.utn.frba.mobile.regalapp.items

import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.fededri.arch.ArchViewModel
import io.github.fededri.arch.ThreadInfo
import javax.inject.Inject

class ItemsViewModel(
    processor: ItemsProcessor,
    updater: ItemsUpdater,
    threadInfoProvider: ThreadInfo
) : ArchViewModel<ItemsActions, ItemsState, ItemSideEffects, ListEvents, Nothing>(
    updater,
    initialState = defaultItemState(),
    threadInfo = threadInfoProvider,
    processor = processor
) {

    class Factory @Inject constructor(
        private val processor: ItemsProcessor,
        private val updater: ItemsUpdater
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ItemsViewModel(
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