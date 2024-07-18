package com.app.ichsanulalifwan.barani.benchmark.flow.database

import android.annotation.SuppressLint
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.ichsanulalifwan.barani.benchmark.api.DatabaseBenchmark
import com.app.ichsanulalifwan.barani.benchmark.flow.FlowBenchmark
import com.app.ichsanulalifwan.barani.benchmark.mock.getMockNewsEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@SuppressLint("CheckResult")
@RunWith(AndroidJUnit4::class)
class FlowDatabaseBenchmark : FlowBenchmark(), DatabaseBenchmark {

    @Test
    override fun queryOneNews() = benchmarkRule.measureRepeated {
        runWithTimingDisabled {
            clearAndInsertMovies(size = 1)
        }
        runBlocking {
            repository.news.first()
        }
    }

    @Test
    override fun queryFiveNews() = benchmarkRule.measureRepeated {
        runWithTimingDisabled {
            clearAndInsertMovies(size = 5)
        }
        runBlocking {
            repository.news.first()
        }
    }

    @Test
    override fun queryTenNews() = benchmarkRule.measureRepeated {
        runWithTimingDisabled {
            clearAndInsertMovies(size = 10)
        }
        runBlocking {
            repository.news.first()
        }
    }

    @Test
    override fun queryTwentyFiveNews() = benchmarkRule.measureRepeated {
        runWithTimingDisabled {
            clearAndInsertMovies(size = 25)
        }
        runBlocking {
            repository.news.first()
        }
    }

    @Test
    override fun queryFiftyNews() = benchmarkRule.measureRepeated {
        runWithTimingDisabled {
            clearAndInsertMovies(size = 50)
        }
        runBlocking {
            repository.news.first()
        }
    }

    @Test
    override fun queryOneHundredNews() = benchmarkRule.measureRepeated {
        runWithTimingDisabled {
            clearAndInsertMovies(size = 100)
        }
        runBlocking {
            repository.news.first()
        }
    }

    private fun clearAndInsertMovies(size: Int) {
        runBlocking {
            val listToInsert = List(size) {
                getMockNewsEntity(it)
            }
            repository.insertNewsToDatabase(listToInsert)
        }
    }
}