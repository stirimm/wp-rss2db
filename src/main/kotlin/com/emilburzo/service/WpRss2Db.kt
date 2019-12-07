package com.emilburzo.service

import com.emilburzo.db.Db
import com.emilburzo.service.http.Http
import com.emilburzo.service.rss.Rss
import org.slf4j.LoggerFactory


/**
 * Created by emil on 01.12.2019.
 */
class WpRss2Db(
    private val db: Db,
    private val http: Http,
    private val rss: Rss
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun run() {
        val news = NEWS_RSS_URLS
            .map { getNews(it.source, it.url) }
            .flatten()

        db.persist(news)
    }

    private fun getNews(source: String, url: String): List<News> {
        return try {
            val content = http.getContent(url) ?: return emptyList()
            rss.getNewsEntries(source, content)
        } catch (e: Exception) {
            logger.warn("error for $url: ${e.message}")
            emptyList()
        }
    }

}