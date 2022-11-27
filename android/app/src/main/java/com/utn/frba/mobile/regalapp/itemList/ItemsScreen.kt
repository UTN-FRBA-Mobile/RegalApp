package com.utn.frba.mobile.regalapp.itemList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.regalapp.R

@Composable
fun ItemScreen(viewModel: ItemsViewModel) {
    val state = viewModel.observeRenderState().collectAsState(initial = ItemsListRenderState()).value
    require(state != null) {
        "Cannot get items screen state"
    }
    val items = state.items

    Scaffold(
        topBar = {
            ItemsScreenTopBar(
                title = state.title,
                onBackClick = {
                    viewModel.action(ItemsActions.GoBack)
                },
                onSettingsClick = {
                    viewModel.action(ItemsActions.OpenEventDetails)
                },
                onShareClick = {
                    viewModel.action(ItemsActions.ShareInvitationToEvent)
                }
            )
        },
        content = { innerPadding ->
            ItemList(
                items = items, contentPadding = innerPadding,
                onItemClick = {
                    viewModel.action(ItemsActions.OpenItemDetails(it))
                },
                actionDispatcher = {
                    viewModel.action(it)
                }
            )
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
fun ItemsScreenTopBar(
    title: String,
    onSettingsClick: () -> Unit,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit
) {
    TopAppBar(title = {
        Image(
            painter = painterResource(id = R.drawable.event_placeholder),
            contentDescription = title,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = title)
    }, actions = {
        IconButton(onClick = onShareClick) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(id = R.string.share_event)
            )
        }

        IconButton(onClick = {
            onSettingsClick()
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
