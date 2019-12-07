package com.emilburzo

import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import okhttp3.OkHttpClient
import okhttp3.Request
import java.time.Instant
import java.time.ZoneId


/**
 * Created by emil on 01.12.2019.
 */

val httpClient = OkHttpClient()

class WpRss2Db(private val db: Db) {

    fun run() {
        val news = NEWS_RSS_URLS
            .map { (source, baseUrl) ->
                val request = Request.Builder().url(baseUrl).build()
                val result =
                    SyndFeedInput().build(XmlReader(httpClient.newCall(request).execute().body?.byteStream()))
                Pair(source, result.entries)
            }
            .flatMap { (source, results) ->
                results.map {
                    News(
                        title = it.title.trim(),
                        url = it.link,
                        published = Instant.ofEpochMilli(it.publishedDate.time).atZone(ZoneId.systemDefault()).toLocalDateTime(),
                        source = source
                    )
                }
            }

        db.persist(news)
    }

}

val NEWS_RSS_URLS = setOf(
    Pair("emaramures", "https://www.emaramures.ro/feed/"),
    Pair("jurnalmm", "http://jurnalmm.ro/feed/"),
    Pair("vasiledale", "https://vasiledale.ro/feed/"),
    Pair("ziarmm", "https://ziarmm.ro/feed/"),
    Pair("actualmm", "http://www.actualmm.ro/feed/"),
    Pair("directmm", "https://www.directmm.ro/feed/")
)