package com.utn.frba.mobile.regalapp.items

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
import androidx.navigation.navGraphViewModels
import com.squareup.anvil.annotations.ContributesMultibinding
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.domain.di.FragmentKey
import com.utn.frba.mobile.regalapp.ItemScreen
import com.utn.frba.mobile.regalapp.R
import javax.inject.Inject

@FragmentKey(ItemListFragment::class)
@ContributesMultibinding(ActivityScope::class, Fragment::class)
class ItemListFragment @Inject constructor(
    private val viewModelFactory: ItemsViewModel.Factory
) : Fragment() {
    private val viewModel: ItemsViewModel by navGraphViewModels(R.id.itemListFragment) { viewModelFactory }

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
}
