package com.pajokka.manggala.maki.utils

import com.pajokka.manggala.maki.model.ArticlesItem
import com.pajokka.manggala.maki.model.News
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object DataMapper {

    fun mapResponseToModel(input: List<ArticlesItem>): List<News> {
        val newsList = ArrayList<News>()
        input.map {
            val news =
                News(
                    title = it.title,
                    date = it.publishedAt,
                    image = it.urlToImage,
                    desc = it.description,
                    url = it.url
                )
            newsList.add(news)
        }
        return newsList
    }

    fun newsDateFormatter(date: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = formatter.parse(date)
        val newFormat = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())
        return newFormat.format(date)
    }

    fun kknDateFormatter(milliseconds: Long): String {
        val date = Date(milliseconds)
        val newFormat = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())
        return newFormat.format(date)
    }
}