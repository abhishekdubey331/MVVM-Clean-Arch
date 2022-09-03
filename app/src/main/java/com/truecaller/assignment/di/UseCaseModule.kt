package com.truecaller.assignment.di

import android.app.Application
import com.truecaller.assignment.domain.repository.contract.BlogContentRepository
import com.truecaller.assignment.domain.usecase.contract.GetEveryNthCharUseCase
import com.truecaller.assignment.domain.usecase.contract.GetNthCharUseCase
import com.truecaller.assignment.domain.usecase.contract.GetWordCounterUseCase
import com.truecaller.assignment.domain.usecase.impl.GetEveryNthCharUseCaseImpl
import com.truecaller.assignment.domain.usecase.impl.GetNthCharUseCaseImpl
import com.truecaller.assignment.domain.usecase.impl.GetWordCounterUseCaseImpl
import com.truecaller.assignment.utils.StringUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(ViewModelComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun getNthCharUseCase(
        blogContentRepository: BlogContentRepository,
        stringUtils: StringUtils,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): GetNthCharUseCase = GetNthCharUseCaseImpl(
        blogContentRepository, stringUtils, coroutineDispatcher
    )

    @Provides
    fun provideStringUtils(app: Application): StringUtils = StringUtils(app)

    @Provides
    fun getEveryNthCharUseCase(
        blogContentRepository: BlogContentRepository,
        stringUtils: StringUtils,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): GetEveryNthCharUseCase = GetEveryNthCharUseCaseImpl(
        blogContentRepository,
        stringUtils,
        coroutineDispatcher
    )


    @Provides
    fun getWordCounterUseCaseImpl(
        blogContentRepository: BlogContentRepository,
        stringUtils: StringUtils,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): GetWordCounterUseCase = GetWordCounterUseCaseImpl(
        blogContentRepository,
        stringUtils,
        coroutineDispatcher
    )
}

