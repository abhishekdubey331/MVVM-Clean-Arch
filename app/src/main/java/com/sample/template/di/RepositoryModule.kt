package com.sample.template.di

import com.sample.template.api.BlogApi
import com.sample.template.domain.repository.contract.BlogContentRepository
import com.sample.template.domain.repository.impl.BlogContentRepositoryImpl
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
    fun provideBlogContentRepository(blogApi: BlogApi): BlogContentRepository =
        BlogContentRepositoryImpl(blogApi)
}
