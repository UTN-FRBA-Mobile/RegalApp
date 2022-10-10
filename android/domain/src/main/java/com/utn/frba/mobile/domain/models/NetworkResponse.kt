package com.utn.frba.mobile.domain.models

sealed class NetworkResponse<T>(val data: T?) {
    class Success<T>(response: T) : NetworkResponse<T>(response)
    class Error<T>(message: String, response: T? = null) : NetworkResponse<T>(response)
}
