package com.codestomp.task.data.repository


import com.codestomp.task.data.network.BaseRepo
import com.codestomp.task.data.network.apisInterfaces.HomeService

class HomeRepo(private val api: HomeService) :

    BaseRepo() {

    suspend fun getArticles() = safeApiCalls {
        api.getArticles()
    }

 suspend fun getArticlesTwo() = safeApiCalls {
        api.getArticlesTwo()
    }




}