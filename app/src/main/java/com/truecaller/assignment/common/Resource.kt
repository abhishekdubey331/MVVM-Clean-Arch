package com.truecaller.assignment.common

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Failure(val errorMessage: String?) : UiState<Nothing>()
}
