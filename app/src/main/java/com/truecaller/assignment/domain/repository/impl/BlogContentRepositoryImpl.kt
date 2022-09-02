package com.truecaller.assignment.domain.repository.impl

import com.truecaller.assignment.api.BlogApi
import com.truecaller.assignment.domain.repository.contract.BlogContentRepository
import javax.inject.Inject

class BlogContentRepositoryImpl @Inject constructor(
    private val blogApi: BlogApi
) : BlogContentRepository {

    override suspend fun fetchBlogContent() = blogApi.fetchBlogContent()
}
