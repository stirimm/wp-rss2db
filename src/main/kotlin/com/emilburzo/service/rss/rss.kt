package com.emilburzo.service.rss

import com.emilburzo.service.News
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import java.time.Instant
import java.time.ZoneId

/**
 * Created by emil on 07.12.2019.
 */
class Rss {

    fun getNewsEntries(source: String, content: String): List<News> {
        val result = SyndFeedInput().build(XmlReader(content.byteInputStream()))

        return result.entries.map {
            News(
                title = it.title.trim(),
                url = it.link,
                published = Instant.ofEpochMilli(it.publishedDate.time).atZone(ZoneId.systemDefault()).toLocalDateTime(),
                source = source
            )
        }
    }
}