package com.sample.template.domain.usecase.contract

import com.sample.template.common.UiState
import kotlinx.coroutines.flow.Flow

interface GetWordCounterUseCase {

    operator fun invoke(): Flow<UiState<String>>

}
