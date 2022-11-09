package com.utn.frba.mobile.regalapp.itemDetail

import androidx.compose.runtime.Composable
import com.utn.frba.mobile.domain.models.ItemModel
import com.utn.frba.mobile.regalapp.addItem.AddItem


@Composable
fun ItemDetail(item: ItemModel?) {
    if (item != null) {
        AddItem(
            item = item,
            readOnly = true,
        )
    }
}