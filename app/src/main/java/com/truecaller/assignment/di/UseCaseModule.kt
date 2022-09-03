package com.truecaller.assignment.di

import com.truecaller.assignment.domain.repository.contract.BlogContentRepository
import com.truecaller.assignment.domain.usecase.contract.GetEveryNthCharUseCase
import com.truecaller.assignment.domain.usecase.contract.GetNthCharUseCase
import com.truecaller.assignment.domain.usecase.contract.GetWordCounterUseCase
import com.truecaller.assignment.domain.usecase.impl.GetEveryNthCharUseCaseImpl
import com.truecaller.assignment.domain.usecase.impl.GetNthCharUseCaseImpl
import com.truecaller.assignment.domain.usecase.impl.GetWordCounterUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(ViewModelComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun getNthCharUseCase(
        blogContentRepository: BlogContentRepository,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): GetNthCharUseCase = GetNthCharUseCaseImpl(
        blogContentRepository, coroutineDispatcher
    )

    @Provides
    fun getEveryNthCharUseCase(
        blogContentRepository: BlogContentRepository,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): GetEveryNthCharUseCase = GetEveryNthCharUseCaseImpl(
        blogContentRepository,
        coroutineDispatcher
    )


    @Provides
    fun getWordCounterUseCaseImpl(
        blogContentRepository: BlogContentRepository,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): GetWordCounterUseCase = GetWordCounterUseCaseImpl(
        blogContentRepository,
        coroutineDispatcher
    )
}

