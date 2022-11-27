package com.utn.frba.mobile.regalapp.eventList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.anvil.annotations.ContributesMultibinding
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.domain.di.FragmentKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@FragmentKey(EventListFragment::class)
@ContributesMultibinding(ActivityScope::class, Fragment::class)
class EventListFragment @Inject constructor(
    private val viewModelFactory: EventsViewModel.Factory
) : Fragment() {

    private val viewModel: EventsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    EventListScreen(viewModel)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeEvents()
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            viewModel.action(EventsActions.SetDeviceToken(token))
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.action(EventsActions.FetchInitialList)
    }

    private fun observeEvents() {
        Timber.i("Events list: subscribing to events")
        lifecycleScope.launch {
            viewModel.observeEvents()
                .flowOn(Dispatchers.Main)
                .collect { event ->
                    Timber.i("Events list received event: $event")
                    when (event) {
                        is ListEvents.OpenEventDetails -> {
                            findNavController().navigate(EventListFragmentDirections.openEventDetailFragment())
                        }

                        is ListEvents.OpenItemsList -> {
                            findNavController().navigate(EventListFragmentDirections.openItemListFragment(event.event.id, event.event.name))
                        }

                        is ListEvents.OpenAddEventScreen -> {
                            findNavController().navigate(EventListFragmentDirections.openAddEventFragment())
                        }
                    }
                }
        }
    }
}
