package com.app.ichsanulalifwan.barani.core.data.source.remote.network.flow

import com.app.ichsanulalifwan.barani.core.data.source.remote.response.NewsResponse
import com.app.ichsanulalifwan.barani.core.data.source.remote.response.PublisherResponse
import com.app.ichsanulalifwan.barani.core.utils.Constant
import com.app.ichsanulalifwan.barani.core.utils.Constant.HEALTH_CATEGORY
import com.app.ichsanulalifwan.barani.core.utils.Constant.US_COUNTRY_CODE
import retrofit2.http.GET
import retrofit2.http.Query

interface FlowNewsApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = US_COUNTRY_CODE,
        @Query("category") category: String = HEALTH_CATEGORY,
        @Query("apiKey") apiKey: String = Constant.NEWS_API_KEY,
    ): NewsResponse

    @GET("v2/top-headlines/sources")
    suspend fun getNewsPublishers(
        @Query("apiKey") apiKey: String = Constant.NEWS_API_KEY,
    ): PublisherResponse

    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q") country: String = US_COUNTRY_CODE,
        @Query("apiKey") apiKey: String = Constant.NEWS_API_KEY,
    ): NewsResponse
}