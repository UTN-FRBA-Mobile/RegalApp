package com.utn.frba.mobile.regalapp.editItem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
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
fun EditItem(
    item: ItemModel?,
    onNameChange: (String) -> Unit? = {},
    onQuantityChange: (String) -> Unit = {},
    onPriceChange: (String) -> Unit = {},
    onLocationChange: (String) -> Unit = {},
    onSaveClicked: (item: ItemModel) -> Unit = {},
    isLoading: Boolean = false,
) {
    if (item != null) {
        Column() {
            ItemForm(
                item = item,
                onNameChange,
                onQuantityChange,
                onPriceChange,
                onLocationChange,
                readOnly = isLoading,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    onSaveClicked(item)
                },
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.edit))
            }
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )
            }
        }
    }
}