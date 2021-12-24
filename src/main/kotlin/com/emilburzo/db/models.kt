package com.emilburzo.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime

/**
 * Created by emil on 07.12.2019.
 */
object DbNews : Table(name = "news") {
    // timezone is lost at the moment - https://github.com/JetBrains/Exposed/issues/221
    // but since all Wordpress RSS feeds are in UTC, that's ok as they are comparable
    val publishDate = datetime(name = "publish_date").index()
    val ingestDate = datetime(name = "ingest_date").clientDefault { DateTime() }
    val title = varchar(name = "title", length = 512)
    val description = text(name = "description")
    val url = varchar(name = "url", length = 512).uniqueIndex()
    val sourceName = varchar(name = "source", length = 100)
}