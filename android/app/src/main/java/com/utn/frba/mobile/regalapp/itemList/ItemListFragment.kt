package com.utn.frba.mobile.regalapp.itemList

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.squareup.anvil.annotations.ContributesMultibinding
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.domain.di.FragmentKey
import com.utn.frba.mobile.regalapp.ItemScreen
import com.utn.frba.mobile.regalapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentKey(ItemListFragment::class)
@ContributesMultibinding(ActivityScope::class, Fragment::class)
class ItemListFragment @Inject constructor(
    private val viewModelFactory: ItemsViewModel.Factory
) : Fragment() {
    private val viewModel: ItemsViewModel by navGraphViewModels(R.id.navigation_main) { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                // TODO: Remove placeholder, add content
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   ItemScreen(viewModel)
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
                            navigateToDestination(ItemListFragmentDirections.openEventDetailFragment())
                        }

                        is ListEvents.OpenAddItemScreen -> {
                            navigateToDestination(ItemListFragmentDirections.addItemFragment())
                        }

                        is ListEvents.OpenItemDetails -> {
                            navigateToDestination(ItemListFragmentDirections.openItemDetailFragment())
                        }

                        is ListEvents.OpenEventsList -> {
                            navigateToDestination(ItemListFragmentDirections.openEventsListFragment())
                        }
                    }
                }
        }
    }

    private fun navigateToDestination(directions: NavDirections) {
        val navController = findNavController()
        val currentDestinationId = navController.currentDestination?.id
        if (currentDestinationId == R.id.itemListFragment) {
            navController.navigate(directions)
        }
    }
}
