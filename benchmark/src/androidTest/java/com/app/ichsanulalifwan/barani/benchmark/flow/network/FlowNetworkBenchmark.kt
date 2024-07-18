package com.app.ichsanulalifwan.barani.benchmark.flow.network

import android.annotation.SuppressLint
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.ichsanulalifwan.barani.benchmark.api.NetworkBenchmark
import com.app.ichsanulalifwan.barani.benchmark.flow.FlowBenchmark
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@SuppressLint("CheckResult")
@RunWith(AndroidJUnit4::class)
class FlowNetworkBenchmark : FlowBenchmark(), NetworkBenchmark {

    @Test
    override fun fetchNewsSingleRequest() = benchmarkRule.measureRepeated {
        runBlocking {
            remoteDataSource.getTopHeadlines()
        }
    }

    @Test
    override fun fetchNewsFiveSequentialRequests() = benchmarkRule.measureRepeated {
        runBlocking {
            remoteDataSource.apply {
                getTopHeadlines()
                getTopHeadlines()
                getTopHeadlines()
                getTopHeadlines()
                getTopHeadlines()
            }
        }
    }

    @Test
    override fun fetchTenNews() = benchmarkRule.measureRepeated {
        val countryCode = List(10) { "us" }
        runBlocking {
            repository.getEverythingNews(countryCode).first()
        }
    }

    @Test
    override fun fetchTwentyFiveNews() = benchmarkRule.measureRepeated {
        val countryCode = List(25) { "us" }
        runBlocking {
            repository.getEverythingNews(countryCode).first()
        }
    }

    @Test
    override fun fetchFiftyNews() = benchmarkRule.measureRepeated {
        val countryCode = List(50) { "us" }
        runBlocking {
            repository.getEverythingNews(countryCode).first()
        }
    }

    @Test
    override fun fetchOneHundredNews() = benchmarkRule.measureRepeated {
        val countryCode = List(100) { "us" }
        runBlocking {
            repository.getEverythingNews(countryCode).first()
        }
    }
}