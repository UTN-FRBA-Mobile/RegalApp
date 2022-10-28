package com.utn.frba.mobile.regalapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.domain.models.EventModel
import com.utn.frba.mobile.regalapp.itemList.ItemList
import com.utn.frba.mobile.regalapp.itemList.ItemsActions
import com.utn.frba.mobile.regalapp.itemList.ItemsViewModel
import com.utn.frba.mobile.regalapp.itemList.defaultItemState

@Composable
fun ItemScreen(viewModel: ItemsViewModel) {


    val state = viewModel.observeState().collectAsState(initial = defaultItemState())
    val event = state.value.selectedEvent

    Scaffold(
        topBar = {
            ItemsScreenTopBar(
                event = event,
                onBackClick = {
                viewModel.action(ItemsActions.OpenEventsList)
                },
                onSettingsClick = {
                    viewModel.action(ItemsActions.OpenEventDetails(it))
                },
            )
        },
        content = { innerPadding ->
            ItemList(items = event.items, contentPadding = innerPadding) {
                viewModel.action(ItemsActions.OpenItemDetails(it))
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.action(ItemsActions.AddItemClicked)
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.add_item)
                )
            }
        }
    )
}

@Composable
fun ItemsScreenTopBar(event: EventModel, onSettingsClick: (EventModel) -> Unit, onBackClick: () -> Unit) {
    TopAppBar(title = {
        Image(
            painter = painterResource(id = R.drawable.event_placeholder),
            contentDescription = event.name,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = event.name)
    }, actions = {
         IconButton(onClick = {
             onSettingsClick(event)
         }) {
             Icon(
                 imageVector = Icons.Filled.Settings,
                 contentDescription = stringResource(id = R.string.open_event)
             )
         }
    }, navigationIcon = {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(onClick = {
                onBackClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }
    })
}
