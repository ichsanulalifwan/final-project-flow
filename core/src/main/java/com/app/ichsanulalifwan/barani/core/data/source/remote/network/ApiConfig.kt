package com.app.ichsanulalifwan.barani.core.data.source.remote.network

import android.content.Context
import com.app.ichsanulalifwan.barani.core.data.source.remote.network.flow.FlowNewsApiService
import com.app.ichsanulalifwan.barani.core.mock.MockInterceptor
import com.app.ichsanulalifwan.barani.core.utils.Constant.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {

    private fun createHttpClient(interceptor: Interceptor? = null): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .apply { interceptor?.let { addInterceptor(it) } }
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun createRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getApiService(): FlowNewsApiService {
        val client = createHttpClient()
        val retrofit = createRetrofit(client)
        return retrofit.create(FlowNewsApiService::class.java)
    }

    fun getMockApiService(context: Context): FlowNewsApiService {
        val mockInterceptor = MockInterceptor(context = context)
        val client = createHttpClient(mockInterceptor)
        val retrofit = createRetrofit(client)
        return retrofit.create(FlowNewsApiService::class.java)
    }

}