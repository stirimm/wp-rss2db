package com.emilburzo

import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.time.ZoneId


/**
 * Created by emil on 01.12.2019.
 */

val httpClient = OkHttpClient()

class WpRss2Db {

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

        persist(news)
    }

    fun persist(news: List<News>) {
        Database.connect(
            url = "jdbc:postgresql://192.168.0.4:5432/stirimm",
            driver = "org.postgresql.Driver",
            user = "stirimm",
            password = "stirimm"
        )

        transaction {
            // print sql to std-out
            addLogger(StdOutSqlLogger)

            // create table if it doesn't already exist
            SchemaUtils.create(DbNews)

            // for the urls we would attempt to insert, check if any of them already exist
            val newUrls = news.map { it.url }.toSet()
            val existingUrls = DbNews.select { DbNews.url.inList(newUrls) }
                .map { it[DbNews.url] }
                .toSet()

            for (newsItem in news) {
                // skip URLs that are already in the database
                if (newsItem.url in existingUrls) {
                    continue
                }

                DbNews.insert {
                    it[sourceName] = newsItem.source
                    it[publishDate] = newsItem.published
                    it[title] = newsItem.title
                    it[url] = newsItem.url
                }
            }
        }
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