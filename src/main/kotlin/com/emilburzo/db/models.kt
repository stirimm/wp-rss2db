package com.emilburzo.db

import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.datetime
import org.joda.time.DateTime

/**
 * Created by emil on 07.12.2019.
 */
object DbNews : LongIdTable(name = "news") {
    val sourceName = varchar(name = "source", length = 100)
    val publishDate = datetime(name = "publish_date").index()
    val ingestDate = datetime(name = "ingest_date").clientDefault { DateTime() }
    val title = varchar(name = "title", length = 512)
    val url = varchar(name = "url", length = 512).uniqueIndex()
}