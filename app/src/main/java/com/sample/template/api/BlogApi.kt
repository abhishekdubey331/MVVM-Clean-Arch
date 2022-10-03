package com.sample.template.api

import com.sample.template.common.Constants
import retrofit2.http.GET

interface BlogApi {

    @GET(Constants.BLOG_URL)
    suspend fun fetchBlogContent(): String
}
