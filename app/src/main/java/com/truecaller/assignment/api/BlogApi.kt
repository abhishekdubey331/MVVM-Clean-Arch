package com.truecaller.assignment.api

import retrofit2.http.GET

interface BlogApi {

    @GET("life-as-an-android-engineer")
    suspend fun fetchBlogContent(): String
}
