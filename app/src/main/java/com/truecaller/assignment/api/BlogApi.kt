package com.truecaller.assignment.api

import com.truecaller.assignment.common.Constants
import retrofit2.http.GET

interface BlogApi {

    @GET(Constants.BLOG_URL)
    suspend fun fetchBlogContent(): String
}
