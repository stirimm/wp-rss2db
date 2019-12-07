package com.emilburzo

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

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