package com.codestomp.task.data.network.apisInterfaces

import com.codestomp.task.data.models.ApiResponse
import retrofit2.http.GET

interface HomeService {


    @GET("articles?source=associated-press")
    suspend fun getArticles(): ApiResponse

    @GET("articles?source=the-next-web")
    suspend fun getArticlesTwo(): ApiResponse









}