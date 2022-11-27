package com.utn.frba.mobile.regalapp.editItem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.regalapp.R
import com.utn.frba.mobile.regalapp.addItem.AddItemActions
import com.utn.frba.mobile.regalapp.itemList.ItemsActions
import com.utn.frba.mobile.regalapp.itemList.ItemsState
import com.utn.frba.mobile.regalapp.itemList.ItemsViewModel
import timber.log.Timber

@Composable
fun EditItemScreen(viewModel: ItemsViewModel) {
    val state = viewModel.observeState().collectAsState(initial = ItemsState()).value
    Scaffold(
        topBar = {
            EditItemTopBar(
                title = stringResource(id = R.string.edit),
                onCancelClick = {
                    viewModel.action(
                        ItemsActions.CloseEditItem()
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(
                    horizontal = 20.dp
                )
            ) {
                EditItem(
                    item = state.editingItem,
                    onNameChange = {
                       viewModel.action(
                           ItemsActions.SetName(it)
                       )
                    },
                    onQuantityChange = {
                        viewModel.action(
                            ItemsActions.SetQuantity(it)
                        )
                    },
                    onPriceChange = {
                        try {
                            val price = it.toDouble()
                            viewModel.action(ItemsActions.SetPrice(price))
                        } catch (e: Exception) {
                            Timber.e(e)
                        }
                    },
                    onLocationChange = {
                        viewModel.action(
                            ItemsActions.SetLocation(it)
                        )
                    },
                    onCoordinatesChange = { lat, lng ->
                        viewModel.action(
                            ItemsActions.SetCoordinates(lat, lng)
                        )
                    },
                    onSaveClicked = {
                        viewModel.action(
                            ItemsActions.UpdateItemClicked(it)
                        )
                    },
                    isLoading = state.isLoading,
                )
            }
        }
    )
}

@Composable
fun EditItemTopBar(
    title: String,
    onCancelClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(title)
        },
        navigationIcon = {
            IconButton(onClick = {
                onCancelClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.cancel)
                )
            }
        }
    )
}
