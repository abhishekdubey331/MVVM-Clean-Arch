package com.truecaller.assignment.domain.repository.contract


interface BlogContentRepository {

    suspend fun fetchBlogContent(): String

}
