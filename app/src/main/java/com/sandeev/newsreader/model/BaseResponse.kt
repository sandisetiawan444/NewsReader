package com.sandeev.newsreader.model

data class BaseResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Article>?
)