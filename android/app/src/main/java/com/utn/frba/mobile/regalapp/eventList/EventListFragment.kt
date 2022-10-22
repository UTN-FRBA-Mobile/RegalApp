package com.utn.frba.mobile.regalapp.eventList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.squareup.anvil.annotations.ContributesMultibinding
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.domain.di.FragmentKey
import com.utn.frba.mobile.regalapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@FragmentKey(EventListFragment::class)
@ContributesMultibinding(ActivityScope::class, Fragment::class)
class EventListFragment @Inject constructor(
    private val viewModelFactory: EventsViewModel.Factory
) : Fragment() {

    private val viewModel: EventsViewModel by navGraphViewModels(R.id.navigation_main) { viewModelFactory }

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

    override fun onResume() {
        super.onResume()
        observeEvents()
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            viewModel.observeEvents()
                .flowOn(Dispatchers.Main)
                .collect { event ->
                    when (event) {
                        is ListEvents.OpenEventDetails -> {
                            navigateToDestination(EventListFragmentDirections.openEventDetailFragment())
                        }

                        is ListEvents.OpenAddEventScreen -> {
                            navigateToDestination(EventListFragmentDirections.openAddEventFragment())
                        }
                    }
                }
        }
    }

    private fun navigateToDestination(directions: NavDirections) {
        val navController = findNavController()
        val currentDestinationId = navController.currentDestination?.id
        if (currentDestinationId == R.id.eventListFragment) {
            navController.navigate(directions)
        }
    }
}
