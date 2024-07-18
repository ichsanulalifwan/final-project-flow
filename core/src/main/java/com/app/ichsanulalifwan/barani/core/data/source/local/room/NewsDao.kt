package com.app.ichsanulalifwan.barani.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.ichsanulalifwan.barani.core.data.source.local.entity.NewsEntity
import com.app.ichsanulalifwan.barani.core.data.source.local.entity.PublisherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    /* Kotlin FLow */

    /**
     * Load data.
     */
    @Query("SELECT * FROM news_entities")
    fun allNewsByFlow(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM publisher_entities")
    fun allPublisherByFlow(): Flow<List<PublisherEntity>>

    /**
     * Insert data.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsEntities: List<NewsEntity>)

    @Query("DELETE FROM news_entities")
    suspend fun deleteAllNews()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPublisher(publisherEntities: List<PublisherEntity>)

    @Query("DELETE FROM publisher_entities")
    suspend fun deleteAllPublishers()
}