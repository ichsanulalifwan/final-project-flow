package com.app.ichsanulalifwan.barani.ui.beranda

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.ichsanulalifwan.barani.core.R
import com.app.ichsanulalifwan.barani.core.data.repository.location.AddressRepository
import com.app.ichsanulalifwan.barani.core.data.repository.news.flow.FlowNewsRepository
import com.app.ichsanulalifwan.barani.core.model.News
import com.app.ichsanulalifwan.barani.core.model.Publisher
import com.app.ichsanulalifwan.barani.core.utils.DataMapper
import com.app.ichsanulalifwan.barani.core.viewmodel.BaseViewModel
import com.app.ichsanulalifwan.barani.utils.Constant.HEALTH_CATEGORY
import com.app.ichsanulalifwan.barani.utils.Constant.US_COUNTRY_CODE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class BerandaViewModel(
    application: Application,
    private val newsRepository: FlowNewsRepository,
    private val addressRepository: AddressRepository,
) : BaseViewModel(application) {

    private var locationJobFlow: Flow<Unit>? = null

    init {
        getTopHeadlineNews()
    }

    override fun getNews(): LiveData<List<News>> = newsRepository.news
            .map { entityList ->
                DataMapper.mapNewsEntityToModel(entityList)
            }
            .flowOn(Dispatchers.IO)
            .asLiveData()

    override fun getPublishers(): LiveData<List<Publisher>> = newsRepository.publishers
        .map { entityList ->
            DataMapper.mapPublisherListToModel(entityList)
        }
        .flowOn(Dispatchers.IO)
        .asLiveData()

    override fun getTopHeadlineNews() {
        startTopHeadlinesNewsTimer()
        viewModelScope.launch {
            newsRepository.getTopHeadlineNews(
                countryCode = US_COUNTRY_CODE,
                category = HEALTH_CATEGORY,
            )
                .flowOn(Dispatchers.IO)
                .onStart {
                    isLoading.value = true
                }
                .catch { throwable ->
                    message.value = context.getString(R.string.news_error)
                    Log.e(LOG_TAG, "Could not fetch news", throwable)
                }
                .onCompletion {
                    stopTopHeadlinesNewsTimer()
                    isLoading.value = false
                }.collect {
                    isLocalNews.value = false
                }
        }
    }

    override fun getNewsPublisher() {
        startSourcesNewsTimer()

        viewModelScope.launch {
            newsRepository.getNewsPublisher()
                .flowOn(Dispatchers.IO)
                .onStart {
                    isLoading.value = true
                }
                .catch { throwable ->
                    message.value = context.getString(R.string.news_error)
                    Log.e(LOG_TAG, "Could not fetch publisher", throwable)
                }
                .onCompletion {
                    stopSourcesNewsTimer()
                    isLoading.value = false
                }.collect {
                    isLocalNews.value = false
                }
        }
    }

    override fun startUpdatesForTopHeadlineNewsLocal() {
        TODO("Not yet implemented")
    }

    override fun cancelUpdatesForTopHeadlineNewsLocal() {
        locationJobFlow = null
    }
}