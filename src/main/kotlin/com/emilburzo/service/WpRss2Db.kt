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
            .map { getNews(it) }
            .flatten()

        db.persist(news)
    }

    private fun getNews(newsRssUrl: NewsRssUrl): List<News> {
        return try {
            val content = http.getContent(newsRssUrl.url) ?: return emptyList()
            rss.getNewsEntries(newsRssUrl, content)
        } catch (e: Exception) {
            logger.warn("error for ${newsRssUrl.url}: ${e.message}")
            emptyList()
        }
    }

}