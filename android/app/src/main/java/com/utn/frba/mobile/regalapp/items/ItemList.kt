package com.utn.frba.mobile.regalapp.events

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun ItemList(items: List<ItemModel>, contentPadding: PaddingValues) {
    LazyColumn(
        contentPadding = contentPadding,
    ) {
        items(items) { item ->
            Spacer(modifier = Modifier.height(Dp(20F)))
            ItemCard(item)
        }
    }
}
