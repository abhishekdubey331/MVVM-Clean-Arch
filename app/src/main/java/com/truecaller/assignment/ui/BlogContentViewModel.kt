package com.truecaller.assignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truecaller.assignment.common.UiState
import com.truecaller.assignment.domain.usecase.contract.GetEveryNthCharUseCase
import com.truecaller.assignment.domain.usecase.contract.GetNthCharUseCase
import com.truecaller.assignment.domain.usecase.contract.GetWordCounterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogContentViewModel @Inject constructor(
    private val getNthCharUseCase: GetNthCharUseCase,
    private val getEveryNthCharUseCase: GetEveryNthCharUseCase,
    private val getWordCounterUseCase: GetWordCounterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BlogContentUiState())

    val state: StateFlow<BlogContentUiState> = _state

    fun getNthChar(n: Int) = viewModelScope.launch {
        getNthCharUseCase(n).collect { result ->
            _state.update {
                when (result) {
                    is UiState.Loading -> it.copy(isLoading = true, errorMessage = null)

                    is UiState.Success -> it.copy(isLoading = false, nthChar = result.data)

                    is UiState.Failure -> it.copy(
                        isLoading = false,
                        errorMessage = result.errorMessage
                    )
                }
            }
        }
    }

    fun getEveryNthChar(n: Int) = viewModelScope.launch {
        getEveryNthCharUseCase(n).collect { result ->
            _state.update {
                when (result) {
                    is UiState.Loading -> it.copy(isLoading = true, errorMessage = null)

                    is UiState.Success -> it.copy(isLoading = false, everyNthChar = result.data!!)

                    is UiState.Failure -> it.copy(
                        isLoading = false,
                        errorMessage = result.errorMessage
                    )
                }
            }
        }
    }

    fun getWordCounter() = viewModelScope.launch {
        getWordCounterUseCase().collect { result ->
            _state.update {
                when (result) {
                    is UiState.Loading -> it.copy(isLoading = true, errorMessage = null)

                    is UiState.Success -> it.copy(wordCount = result.data!!)

                    is UiState.Failure -> it.copy(
                        isLoading = false,
                        errorMessage = result.errorMessage
                    )
                }
            }
        }
    }
}
