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

@InstallIn(ViewModelComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun getNthCharUseCase(blogContentRepository: BlogContentRepository): GetNthCharUseCase =
        GetNthCharUseCaseImpl(
            blogContentRepository
        )

    @Provides
    fun getEveryNthCharUseCase(blogContentRepository: BlogContentRepository): GetEveryNthCharUseCase =
        GetEveryNthCharUseCaseImpl(
            blogContentRepository
        )


    @Provides
    fun getWordCounterUseCaseImpl(blogContentRepository: BlogContentRepository): GetWordCounterUseCase =
        GetWordCounterUseCaseImpl(
            blogContentRepository
        )
}

