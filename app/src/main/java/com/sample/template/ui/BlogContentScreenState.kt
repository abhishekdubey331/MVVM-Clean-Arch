package com.sample.template.ui


data class BlogContentUiState(
    val errorMessage: String? = null,
    val nthChar: String = "",
    val everyNthChar: String = "",
    val wordCount: String = ""
)
