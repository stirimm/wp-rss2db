package com.emilburzo.service

import com.emilburzo.db.Db
import com.emilburzo.service.http.Http
import com.emilburzo.service.rss.Rss
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
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

    fun run() = runBlocking {
        val news = NEWS_RSS_URLS
            .map { async { getNews(it) } }
            .awaitAll()
            .flatten()

        db.persist(news)
    }

    private suspend fun getNews(newsRssUrl: NewsRssUrl): List<News> {
        logger.info("parsing news from $newsRssUrl")

        return try {
            val content = http.getContent(newsRssUrl) ?: return emptyList()
            rss.parseNewsEntries(newsRssUrl, content)
        } catch (e: Exception) {
            logger.warn("error for ${newsRssUrl.url}: ${e.message}")
            emptyList()
        }
    }

}