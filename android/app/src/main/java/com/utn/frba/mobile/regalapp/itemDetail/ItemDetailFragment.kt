package com.utn.frba.mobile.regalapp.itemDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.squareup.anvil.annotations.ContributesMultibinding
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.domain.di.FragmentKey
import com.utn.frba.mobile.regalapp.R
import com.utn.frba.mobile.regalapp.itemList.ItemListFragmentDirections
import com.utn.frba.mobile.regalapp.itemList.ItemsActions
import com.utn.frba.mobile.regalapp.itemList.ItemsViewModel
import com.utn.frba.mobile.regalapp.itemList.ListEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentKey(ItemDetailFragment::class)
@ContributesMultibinding(ActivityScope::class, Fragment::class)
class ItemDetailFragment @Inject constructor(
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ItemDetailScreen(viewModel = viewModel )
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeEvents()
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            viewModel.observeEvents()
                .flowOn(Dispatchers.Main)
                .collect { event ->
                    when (event) {
                        is ListEvents.CloseDetailPressed -> {
                            findNavController().popBackStack()
                        }
                        is ListEvents.OpenEditItemScreen -> {
                            findNavController().navigate(
                                ItemDetailFragmentDirections.openEditItemFragment()
                            )
                        }
                        else -> {}
                    }
                }
        }
    }
}
