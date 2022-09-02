package com.truecaller.assignment.domain.usecase.contract

import com.truecaller.assignment.common.Resource
import kotlinx.coroutines.flow.Flow

interface GetNthCharUseCase {

    operator fun invoke(n: Int): Flow<Resource<Char>>

}