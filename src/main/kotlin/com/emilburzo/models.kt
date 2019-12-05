package com.emilburzo

import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.datetime
import java.time.LocalDateTime

/**
 * Created by emil on 01.12.2019.
 */
data class News(
    val title: String,
    val url: String,
    val published: LocalDateTime,
    val source: String
)

object DbNews : LongIdTable(name = "news") {
    val sourceName = varchar(name = "source", length = 100)
    val publishDate = datetime(name = "publish_date").index()
    val ingestDate = datetime(name = "ingest_date").clientDefault { LocalDateTime.now() }
    val title = varchar(name = "title", length = 512)
    val url = varchar(name = "url", length = 512).uniqueIndex()
}