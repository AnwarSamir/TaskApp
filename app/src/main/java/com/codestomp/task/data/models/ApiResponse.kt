package com.codestomp.task.data.models

data class ApiResponse(
    val articles: ArrayList<Article>,
    val sortBy: String,
    val source: String,
    val status: String
)