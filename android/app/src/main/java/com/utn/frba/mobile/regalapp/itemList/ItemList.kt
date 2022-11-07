package com.utn.frba.mobile.regalapp.itemList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.domain.models.ItemModel
import com.utn.frba.mobile.regalapp.R

@Composable
fun ItemList(items: List<ItemModel>, contentPadding: PaddingValues, onItemClick: (ItemModel) -> Unit, actionDispatcher: (ItemsActions) -> Unit) {
    var itemFilter by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.padding(
            horizontal = 20.dp
        )
    ) {
        OutlinedTextField(
            value = itemFilter,
            label = { Text(text = stringResource(id = R.string.search)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.search)
                )
            },
            onValueChange = { value: String ->
                itemFilter = value
                actionDispatcher(ItemsActions.FilterItems(value))
            }
        )
        LazyColumn(
            contentPadding = contentPadding,
        ) {

            items(items) { item ->
                Spacer(modifier = Modifier.height(20.dp))
                ItemCard(item) {
                    onItemClick(it)
                }
            }
        }
    }
}
