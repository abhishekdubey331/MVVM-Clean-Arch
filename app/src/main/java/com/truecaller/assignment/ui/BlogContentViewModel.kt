package com.truecaller.assignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truecaller.assignment.common.UiState
import com.truecaller.assignment.domain.usecase.contract.GetEveryNthCharUseCase
import com.truecaller.assignment.domain.usecase.contract.GetNthCharUseCase
import com.truecaller.assignment.domain.usecase.contract.GetWordCounterUseCase
import com.truecaller.assignment.utils.StringUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogContentViewModel @Inject constructor(
    private val getNthCharUseCase: GetNthCharUseCase,
    private val getEveryNthCharUseCase: GetEveryNthCharUseCase,
    private val getWordCounterUseCase: GetWordCounterUseCase,
    private val stringUtils: StringUtils
) : ViewModel() {

    private val _state = MutableStateFlow(BlogContentUiState())

    val state = _state.asStateFlow()

    fun getNthChar(n: Int) = viewModelScope.launch {
        getNthCharUseCase(n).collect { result ->
            _state.update {
                when (result) {
                    is UiState.Loading -> it.copy(
                        nthChar = stringUtils.loading(),
                        errorMessage = null
                    )

                    is UiState.Success -> it.copy(nthChar = result.data)

                    is UiState.Failure -> it.copy(errorMessage = result.errorMessage)
                }
            }
        }
    }

    fun getEveryNthChar(n: Int) = viewModelScope.launch {
        getEveryNthCharUseCase(n).collect { result ->
            _state.update {
                when (result) {
                    is UiState.Loading -> it.copy(
                        everyNthChar = stringUtils.loading(),
                        errorMessage = null
                    )

                    is UiState.Success -> it.copy(everyNthChar = result.data)

                    is UiState.Failure -> it.copy(errorMessage = result.errorMessage)
                }
            }
        }
    }

    fun getWordCounter() = viewModelScope.launch {
        getWordCounterUseCase().collect { result ->
            _state.update {
                when (result) {
                    is UiState.Loading -> it.copy(
                        wordCount = stringUtils.loading(),
                        errorMessage = null
                    )

                    is UiState.Success -> it.copy(wordCount = result.data)

                    is UiState.Failure -> it.copy(errorMessage = result.errorMessage)
                }
            }
        }
    }
}
