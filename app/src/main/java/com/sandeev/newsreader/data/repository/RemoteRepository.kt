package com.sandeev.newsreader.data.repository

import com.sandeev.newsreader.data.remote.ApiClient
import com.sandeev.newsreader.data.remote.ApiService
import com.sandeev.newsreader.model.BaseResponse

class RemoteRepository {

    suspend fun getArticles(
        query: String?,
        country: String?,
        category: String?,
        apiKey: String?,
        callback: RepositoryCallback<BaseResponse?>
    ) {
        try {
            val response =
                ApiClient.createService(ApiService::class.java)
                    .getArticlesAsync(query, country, category, apiKey)
                    .await()
            callback.onDataSuccess(response)
        } catch (e: Exception) {
            callback.onDataError(e.message)
        }
    }
}