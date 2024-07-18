package com.app.ichsanulalifwan.barani.core.viewmodel.flow

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.ichsanulalifwan.barani.core.R
import com.app.ichsanulalifwan.barani.core.data.location.flow.getAddresses
import com.app.ichsanulalifwan.barani.core.data.location.flow.getLocationUpdates
import com.app.ichsanulalifwan.barani.core.data.repository.location.AddressRepository
import com.app.ichsanulalifwan.barani.core.data.repository.news.flow.FlowNewsRepository
import com.app.ichsanulalifwan.barani.core.model.News
import com.app.ichsanulalifwan.barani.core.model.Publisher
import com.app.ichsanulalifwan.barani.core.utils.Constant.HEALTH_CATEGORY
import com.app.ichsanulalifwan.barani.core.utils.Constant.US_COUNTRY_CODE
import com.app.ichsanulalifwan.barani.core.utils.DataMapper
import com.app.ichsanulalifwan.barani.core.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FlowViewModel(
    application: Application,
    private val newsRepository: FlowNewsRepository,
    private val addressRepository: AddressRepository,
) : BaseViewModel(application) {

    private val locationJob = Job()
    private var locationJobFlow: Flow<Unit>? = null

    init {
        getTopHeadlineNews()
        getNewsPublisher()
    }

    override fun getNews(): LiveData<List<News>> =
        newsRepository.news
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
                    message.value = context.getString(
                        R.string.news_error
                    )
                    Log.e(LOG_TAG, "Error fetching top headlines", throwable)
                }
                .onCompletion {
                    stopTopHeadlinesNewsTimer()
                    isLoading.value = false
                }.collect {
                    isLocalNews.value = false
                }
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun startUpdatesForTopHeadlineNewsLocal() {
        startTopHeadlineNewsLocalTimer()

        locationJobFlow = getLocationUpdates(
            locationClient = locationServiceClient,
            locationRequest = locationRequest,
        )
            .flowOn(Dispatchers.IO)
            .flatMapConcat { location ->
                getAddresses(
                    addressRepository = addressRepository,
                    location = location,
                    maxResults = 1,
                ).flatMapConcat { addresses ->
                    val countryCode = addresses.first().countryCode
                    newsRepository.getTopHeadlineNews(
                        countryCode = countryCode,
                        category = HEALTH_CATEGORY,
                    ).onCompletion {
                        stopTopHeadlineNewsLocalTimer()
                        isLoading.value = false
                        isLocalNews.value = true
                    }
                }
            }
            .onStart {
                isLoading.value = true
            }
            .catch { throwable ->
                handleTopHeadlineNewsLocalError(throwable)
            }
    }

    override fun cancelUpdatesForTopHeadlineNewsLocal() {
        locationJobFlow = null
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

    override fun onCleared() {
        locationJob.cancel()
        super.onCleared()
    }
}