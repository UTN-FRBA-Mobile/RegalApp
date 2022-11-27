package com.utn.frba.mobile.regalapp.itemDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.domain.models.ItemModel
import com.utn.frba.mobile.regalapp.R
import com.utn.frba.mobile.regalapp.addItem.ItemForm

@Composable
fun ItemDetail(
    item: ItemModel?,
    onChangeStatus: (item: ItemModel) -> Unit,
) {
    if (item != null) {
        Column() {
            ItemForm(
                item = item,
                onCoordinatesChange = { lat, lng ->
                    // TODO Si no pongo esto falla
                    println(lat)
                    println(lng)
                },
                readOnly = true,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    onChangeStatus(item)
                },
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                if (item.status == true) {
                    Text(text = stringResource(id = R.string.unmark_as_bought))
                } else {
                    Text(text = stringResource(id = R.string.mark_as_bought))
                }
            }
        }
    }
}