package com.truecaller.assignment.di

import com.truecaller.assignment.api.BlogApi
import com.truecaller.assignment.domain.repository.contract.BlogContentRepository
import com.truecaller.assignment.domain.repository.impl.BlogContentRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideListRepository(blogApi: BlogApi): BlogContentRepository =
        BlogContentRepositoryImpl(blogApi)
}
