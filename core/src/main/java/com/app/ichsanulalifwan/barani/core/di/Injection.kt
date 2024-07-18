package com.app.ichsanulalifwan.barani.core.di

import android.content.Context
import com.app.ichsanulalifwan.barani.core.data.repository.news.flow.FlowNewsRepository
import com.app.ichsanulalifwan.barani.core.data.source.local.room.AppDatabase
import com.app.ichsanulalifwan.barani.core.data.source.remote.network.ApiConfig

object Injection {

    fun provideRepository(context: Context, isMock: Boolean): FlowNewsRepository {

        val database = AppDatabase.getInstance(context = context.applicationContext)
        val newsDao = database.newsDao()
        val remoteDataSource = if (!isMock) {
            ApiConfig.getApiService()
        } else ApiConfig.getMockApiService(context = context)

        return FlowNewsRepository.getInstance(
            remoteDataSource = remoteDataSource,
            localDataSource = newsDao,
        )
    }
}