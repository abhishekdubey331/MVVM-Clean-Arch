package com.sample.template.domain.usecase.contract

import com.sample.template.common.UiState
import kotlinx.coroutines.flow.Flow

interface GetNthCharUseCase {

    operator fun invoke(n: Int): Flow<UiState<String>>

}
