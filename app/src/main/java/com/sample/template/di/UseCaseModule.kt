package com.sample.template.di

import android.app.Application
import com.sample.template.domain.repository.contract.BlogContentRepository
import com.sample.template.domain.usecase.contract.GetEveryNthCharUseCase
import com.sample.template.domain.usecase.contract.GetNthCharUseCase
import com.sample.template.domain.usecase.contract.GetWordCounterUseCase
import com.sample.template.domain.usecase.impl.GetEveryNthCharUseCaseImpl
import com.sample.template.domain.usecase.impl.GetNthCharUseCaseImpl
import com.sample.template.domain.usecase.impl.GetWordCounterUseCaseImpl
import com.sample.template.utils.StringUtils
import com.sample.template.utils.StringUtilsImpl
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
        stringUtils: StringUtils,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): GetNthCharUseCase = GetNthCharUseCaseImpl(
        blogContentRepository, stringUtils, coroutineDispatcher
    )

    @Provides
    fun provideStringUtils(app: Application): StringUtils = StringUtilsImpl(app)

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
