package com.sample.template.domain.repository.contract


interface BlogContentRepository {

    suspend fun fetchBlogContent(): String

}
