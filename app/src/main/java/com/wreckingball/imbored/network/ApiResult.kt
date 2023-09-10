package com.wreckingball.imbored.network

sealed class ApiResult<T>(
    val data: T? = null,
    val message: String = "",
) {
    class Success<T>(data: T) : ApiResult<T>(data = data)
    class Error<T>(errorMessage: String) : ApiResult<T>(message = errorMessage)
    class Loading<T>() : ApiResult<T>()
}