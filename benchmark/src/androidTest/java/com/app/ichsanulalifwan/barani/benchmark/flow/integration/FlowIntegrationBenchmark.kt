package com.app.ichsanulalifwan.barani.benchmark.flow.integration

import android.annotation.SuppressLint
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.ichsanulalifwan.barani.benchmark.api.IntegrationBenchmark
import com.app.ichsanulalifwan.barani.benchmark.flow.FlowBenchmark
import com.app.ichsanulalifwan.barani.core.utils.toNewsEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@SuppressLint("CheckResult")
@RunWith(AndroidJUnit4::class)
class FlowIntegrationBenchmark : FlowBenchmark(), IntegrationBenchmark {

    @Test
    override fun integration1() = benchmarkRule.measureRepeated {
        runWithTimingDisabled {
            runBlocking { localDataSource.deleteAllNews() }
        }

        runBlocking {
            // Fetch news
            remoteDataSource.getTopHeadlines()
            val news = with(repository) {
                getEverythingNews(List(10) { "us" }).first()
            }

            // Insert news to database
            repository.insertNewsToDatabase(news.map { it.toNewsEntity() })
        }
    }

    @Test
    override fun integration2() = benchmarkRule.measureRepeated {
        runWithTimingDisabled {
            runBlocking { localDataSource.deleteAllNews() }
        }

        runBlocking {
            // Fetch news
            remoteDataSource.apply {
                getTopHeadlines()
                getTopHeadlines()
            }

            val news = with(repository) {
                getEverythingNews(List(10) { "us" }).first()
                getEverythingNews(List(10) { "us" }).first()
            }

            // Insert news to database
            repository.apply {
                insertNewsToDatabase(news.map { it.toNewsEntity() })
                insertNewsToDatabase(news.map { it.toNewsEntity() })
            }
        }
    }

    @Test
    override fun integration3() = benchmarkRule.measureRepeated {
        runWithTimingDisabled {
            runBlocking { localDataSource.deleteAllNews() }
        }

        runBlocking {
            // Fetch news
            remoteDataSource.apply {
                getTopHeadlines()
                getTopHeadlines()
                getTopHeadlines()
                getTopHeadlines()
            }

            val news = with(repository) {
                getEverythingNews(List(10) { "us" }).first()
                getEverythingNews(List(10) { "us" }).first()
                getEverythingNews(List(10) { "us" }).first()
                getEverythingNews(List(10) { "us" }).first()
            }

            // Insert news to database
            repository.apply {
                insertNewsToDatabase(news.map { it.toNewsEntity() })
                insertNewsToDatabase(news.map { it.toNewsEntity() })
                insertNewsToDatabase(news.map { it.toNewsEntity() })
                insertNewsToDatabase(news.map { it.toNewsEntity() })
            }
        }
    }

    @Test
    override fun integration4() = benchmarkRule.measureRepeated {
        runWithTimingDisabled {
            runBlocking { localDataSource.deleteAllNews() }
        }

        runBlocking {
            // Fetch news
            remoteDataSource.apply {
                getTopHeadlines()
                getTopHeadlines()
                getTopHeadlines()
                getTopHeadlines()
                getTopHeadlines()
                getTopHeadlines()
                getTopHeadlines()
                getTopHeadlines()
            }

            val news = with(repository) {
                getEverythingNews(List(10) { "us" }).first()
                getEverythingNews(List(10) { "us" }).first()
                getEverythingNews(List(10) { "us" }).first()
                getEverythingNews(List(10) { "us" }).first()
                getEverythingNews(List(10) { "us" }).first()
                getEverythingNews(List(10) { "us" }).first()
                getEverythingNews(List(10) { "us" }).first()
                getEverythingNews(List(10) { "us" }).first()
            }

            // Insert news to database
            repository.apply {
                insertNewsToDatabase(news.map { it.toNewsEntity() })
                insertNewsToDatabase(news.map { it.toNewsEntity() })
                insertNewsToDatabase(news.map { it.toNewsEntity() })
                insertNewsToDatabase(news.map { it.toNewsEntity() })
                insertNewsToDatabase(news.map { it.toNewsEntity() })
                insertNewsToDatabase(news.map { it.toNewsEntity() })
                insertNewsToDatabase(news.map { it.toNewsEntity() })
                insertNewsToDatabase(news.map { it.toNewsEntity() })
            }
        }
    }
}