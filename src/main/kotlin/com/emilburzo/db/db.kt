package com.emilburzo.db

import com.emilburzo.service.News
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.*

/**
 * Created by emil on 07.12.2019.
 */
class Db(
    private val host: String = System.getenv("DB_HOST"),
    private val port: String = System.getenv("DB_PORT") ?: "5432",
    private val user: String = System.getenv("DB_USER") ?: "stirimm",
    private val pass: String = System.getenv("DB_PASS"),
    private val name: String = System.getenv("DB_NAME") ?: "stirimm"
) {

    fun persist(news: List<News>) {
        Database.connect(
            url = "jdbc:postgresql://$host:$port/$name",
            driver = "org.postgresql.Driver",
            user = user,
            password = pass
        )

        transaction {
            // create table if it doesn't already exist
            SchemaUtils.create(DbNews)

            // for the urls we would attempt to insert, check if any of them already exist
            val newUrls = news.map { it.url }.toSet()
            val existingUrls = DbNews.select { DbNews.url.inList(newUrls) }
                .map { it[DbNews.url] }
                .toMutableSet()

            for (newsItem in news) {
                // skip URLs that are already in the database
                if (newsItem.url in existingUrls) {
                    continue
                }

                val now = Date()
                val plausiblePublishDate = if (newsItem.published.after(now)) {
                    now
                } else {
                    newsItem.published
                }

                DbNews.insert {
                    it[title] = newsItem.title
                    it[description] = newsItem.description
                    it[url] = newsItem.url
                    it[sourceName] = newsItem.source
                    it[publishDate] = DateTime(plausiblePublishDate)
                }

                existingUrls.add(newsItem.url)
            }
        }
    }
}

