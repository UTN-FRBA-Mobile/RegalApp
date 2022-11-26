package com.utn.frba.mobile.regalapp.map

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.regalapp.R
import java.io.IOException

@Composable()
fun LocationField(
    location: String = "",
    latitude: Double? = null,
    longitude: Double? = null,
    onLocationChange: (String) -> Unit = {},
    onCoordinatesChange: (lat: Double, lng: Double) -> Unit = {_, _ -> },
    readOnly: Boolean = false,
) {
    val addressList = remember {
        mutableListOf<Address>()
    }
    val showList = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                value = location,
                onValueChange = {
                    onLocationChange(it)
                },
                placeholder = { Text(text = "Ubicacion") },
                modifier = Modifier
                    .fillMaxWidth(1F)
                    .weight(1F),
                singleLine = true,
                readOnly = readOnly,
            )
            Spacer(modifier = Modifier.width(5.dp))
            if(readOnly) {
                Button(
                    onClick = {
                        val gmmIntentUri = Uri.parse("geo:${latitude},${longitude}?q=${Uri.encode(location)}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        context.startActivity(mapIntent)
                    },
                    modifier = Modifier.height(55.dp),
                    enabled = latitude != null && longitude != null
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = stringResource(id = R.string.open_map)
                    )
                }
            } else {
                Button(
                    onClick = {
                        addressList.clear()
                        getMapLocation(location, context)?.let {
                            addressList.addAll(
                                it
                            )
                        }
                        showList.value = true

                    },
                    modifier = Modifier.height(55.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search)
                    )
                }
            }
        }
        DropdownMenu(expanded = showList.value, onDismissRequest = {
            showList.value = false
        }) {
            if(addressList.size > 0) {
                addressList.forEach {
                    DropdownMenuItem(onClick = {
                        setLocation(it, onLocationChange, onCoordinatesChange)
                        showList.value = false
                    }) {
                        Text(text = it.getAddressLine(0))
                    }
                }   
            } else {
                DropdownMenuItem(onClick = {
                    showList.value = false
                }) {
                        Text(text = "No se encontraron resultados")
                }
            }
        }
    }
    
}

private fun setLocation(address: Address, onLocationChange: (String) -> Unit, onCoordinatesChange: (lat: Double, lng: Double) -> Unit) {
    onLocationChange(address.getAddressLine(0))
    onCoordinatesChange(address.latitude, address.longitude)
}

private fun getMapLocation(location: String, context: Context): List<Address>? {
    var addressList: List<Address>? = null
        if (location != null || location == "") {
            val geocoder = Geocoder(context)
            try {
                addressList = geocoder.getFromLocationName(
                    location,
                    4,
                    -34.99,
                    -58.99,
                    -34.0,
                    -58.00
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return addressList

    }
    return null
}

