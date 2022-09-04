package com.truecaller.assignment.ui


data class BlogContentUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val nthChar: String = "",
    val everyNthChar: String = "",
    val wordCount: String = ""
)
