package com.truecaller.assignment.ui


data class BlogContentScreenState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val nthChar: Char = Char.MIN_VALUE,
    val everyNthChar: String = "",
    val wordCount: String = ""
)