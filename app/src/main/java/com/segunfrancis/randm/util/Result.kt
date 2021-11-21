package com.segunfrancis.randm.util

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val errorMessage: String?) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
