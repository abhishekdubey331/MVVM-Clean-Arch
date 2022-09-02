package com.truecaller.assignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truecaller.assignment.common.Resource
import com.truecaller.assignment.domain.usecase.contract.GetEveryNthCharUseCase
import com.truecaller.assignment.domain.usecase.contract.GetNthCharUseCase
import com.truecaller.assignment.domain.usecase.contract.GetWordCounterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogContentViewModel @Inject constructor(
    private val getNthCharUseCase: GetNthCharUseCase,
    private val getEveryNthCharUseCase: GetEveryNthCharUseCase,
    private val getWordCounterUseCase: GetWordCounterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BlogContentScreenState())

    val state: StateFlow<BlogContentScreenState> = _state

    fun getNthChar(n: Int) {
        viewModelScope.launch {
            getNthCharUseCase(n).collect { result ->

                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            nthChar = result.data ?: Char.MIN_VALUE
                        )

                    }

                    is Resource.Error -> {
                        _state.value =
                            state.value.copy(isLoading = false, message = result.message ?: "")
                    }
                }
            }
        }
    }

    fun getEveryNthChar(n: Int) {
        viewModelScope.launch {
            getEveryNthCharUseCase(n).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _state.value =
                            state.value.copy(isLoading = false, everyNthChar = result.data ?: "")
                    }

                    is Resource.Error -> {
                        _state.value =
                            state.value.copy(isLoading = false, message = result.message ?: "")
                    }
                }
            }
        }
    }

    fun getWordCounter() {
        viewModelScope.launch {
            getWordCounterUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = BlogContentScreenState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(wordCount = result.data ?: "")
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(message = result.message ?: "")
                    }
                }
            }
        }
    }
}
