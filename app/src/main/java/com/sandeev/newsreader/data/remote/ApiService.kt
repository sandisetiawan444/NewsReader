package com.sandeev.newsreader.data.remote

import com.sandeev.newsreader.data.remote.ApiEndpoint.ENDPOINT_ARTICLE
import com.sandeev.newsreader.model.BaseResponse
import com.sandeev.newsreader.util.AppConstants.PARAMETER_API_KEY
import com.sandeev.newsreader.util.AppConstants.PARAMETER_CATEGORY
import com.sandeev.newsreader.util.AppConstants.PARAMETER_COUNTRY
import com.sandeev.newsreader.util.AppConstants.PARAMETER_QUERY
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(ENDPOINT_ARTICLE)
    fun getArticlesAsync(
        @Query(PARAMETER_QUERY) query: String?,
        @Query(PARAMETER_COUNTRY) country: String?,
        @Query(PARAMETER_CATEGORY) category: String?,
        @Query(PARAMETER_API_KEY) key: String?
    ): Deferred<BaseResponse>
}