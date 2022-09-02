package com.truecaller.assignment.domain.usecase.contract

import com.truecaller.assignment.common.Resource
import kotlinx.coroutines.flow.Flow

interface GetWordCounterUseCase {

    operator fun invoke(): Flow<Resource<String>>

}