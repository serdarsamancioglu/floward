package com.serdar.floward.entity

sealed class ApiCallState<out T> {
    data class Success<T>(val item: T): ApiCallState<T>()
    data class Error(val message: String): ApiCallState<Nothing>()
}