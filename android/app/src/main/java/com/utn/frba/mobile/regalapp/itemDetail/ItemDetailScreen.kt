package com.utn.frba.mobile.regalapp.itemDetail

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
import com.utn.frba.mobile.regalapp.itemList.ItemsActions
import com.utn.frba.mobile.regalapp.itemList.ItemsState
import com.utn.frba.mobile.regalapp.itemList.ItemsViewModel

@Composable
fun ItemDetailScreen(viewModel: ItemsViewModel) {
    val state = viewModel.observeState().collectAsState(initial = ItemsState()).value
    Scaffold(
        topBar = {
            ItemDetailTopbar(
                title = state.selectedItem?.name.orEmpty(),
                onCancelClick = {
                    viewModel.action(
                        ItemsActions.CloseItemDetail
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
                ItemDetail(
                    item = state.selectedItem,
                    onChangeStatus = {
                        viewModel.action(
                            ItemsActions.ChangeItemStatus(it)
                        )
                    }
                )
            }
        }
    )
}

@Composable
fun ItemDetailTopbar(
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
