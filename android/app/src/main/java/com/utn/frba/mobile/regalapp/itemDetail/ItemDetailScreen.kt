package com.utn.frba.mobile.regalapp.itemDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.regalapp.R

@Composable
fun AddItemScreen(viewModel: AddItemViewModel) {
    Scaffold(
        topBar = {
            AddItemTopbar(
                onCancelClick = {
                    viewModel.action(
                        AddItemActions.CancelClicked
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
                AddItemForm(viewModel = viewModel)
            }
        }
    )
}

@Composable
fun AddItemTopbar(
    onCancelClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(stringResource(id = R.string.add_item))
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
