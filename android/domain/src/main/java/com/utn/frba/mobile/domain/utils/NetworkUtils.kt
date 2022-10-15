package com.utn.frba.mobile.domain.utils

import com.utn.frba.mobile.domain.models.NetworkResponse

inline fun <T> safeCall(action: () -> NetworkResponse<T>): NetworkResponse<T> {
    return try {
        action()
    } catch (e: Exception) {
        NetworkResponse.Error(e.message ?: "Unknown error")
    }
}
