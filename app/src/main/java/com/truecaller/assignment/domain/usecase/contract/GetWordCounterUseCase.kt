package com.truecaller.assignment.domain.usecase.contract

import com.truecaller.assignment.common.UiState
import kotlinx.coroutines.flow.Flow

interface GetWordCounterUseCase {

    operator fun invoke(): Flow<UiState<String>>

}