package com.emilburzo.service

import com.emilburzo.db.Db
import com.rometools.rome.feed.synd.SyndEntry
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
            .map { getEntries(it.source, it.url) }
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

    private fun getEntries(source: String, url: String): Pair<String, List<SyndEntry>> {
        val request = Request.Builder().url(url).build()
        val result =
            SyndFeedInput().build(XmlReader(httpClient.newCall(request).execute().body?.byteStream()))
        return Pair(source, result.entries)
    }

}