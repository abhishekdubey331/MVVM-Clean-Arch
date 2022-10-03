package com.sample.template.domain.repository.impl

import com.sample.template.api.BlogApi
import com.sample.template.domain.repository.contract.BlogContentRepository
import javax.inject.Inject

class BlogContentRepositoryImpl @Inject constructor(
    private val blogApi: BlogApi
) : BlogContentRepository {

    override suspend fun fetchBlogContent() = blogApi.fetchBlogContent()
}
