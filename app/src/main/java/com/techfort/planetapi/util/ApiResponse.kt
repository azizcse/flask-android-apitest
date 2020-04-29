package com.techfort.planetapi.util

data class ApiResponse<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> loading(): ApiResponse<T> = ApiResponse(Status.LOADING, null, null)
        fun <T> success(data: T): ApiResponse<T> = ApiResponse(Status.SUCCESS, data, null)
        fun <T> error(message: String): ApiResponse<T> = ApiResponse(Status.ERROR, null, message)
    }
}