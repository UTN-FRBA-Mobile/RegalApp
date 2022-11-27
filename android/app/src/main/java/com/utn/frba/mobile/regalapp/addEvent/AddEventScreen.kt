package com.utn.frba.mobile.regalapp.addEvent

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
fun AddEventScreen(viewModel: AddEventViewModel) {
    Scaffold(
        topBar = {
            AddEventTopBar(
                onCancelClick = {
                    viewModel.action(AddEventActions.CancelClicked)
                }
            )
        },
        content = {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                AddEventForm(viewModel = viewModel)
            }
        }
    )
}

@Composable
fun AddEventTopBar(onCancelClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(stringResource(id = R.string.create_event))
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
