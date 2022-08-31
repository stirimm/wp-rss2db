package com.emilburzo.service.rss

import com.emilburzo.service.News
import com.emilburzo.service.NewsRssUrl
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import org.jsoup.Jsoup
import org.jsoup.safety.Whitelist
import org.slf4j.LoggerFactory


/**
 * Created by emil on 07.12.2019.
 */
class Rss {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun parseNewsEntries(newsRssUrl: NewsRssUrl, content: String): List<News> {
        val result = SyndFeedInput().build(XmlReader(content.byteInputStream()))

        return result.entries.map {
            News(
                title = it.title.trim().take(512),
                description = cleanDescription(it.description.value, newsRssUrl),
                url = it.link,
                published = it.publishedDate,
                source = newsRssUrl.source
            )
        }
    }

    private fun cleanDescription(dirtyDescriptionHtml: String, newsRssUrl: NewsRssUrl): String {
        val cleanDescriptionHtml = Jsoup.clean(dirtyDescriptionHtml, Whitelist.none())
        val cleanText = Jsoup.parse(cleanDescriptionHtml).wholeText().trim()
        val lastIndexOfSpam = cleanText.lastIndexOf(newsRssUrl.spamSeparator)
        return if (lastIndexOfSpam != -1) {
            cleanText.substring(0, lastIndexOfSpam)
        } else {
            logger.warn("did not find the spam separator in ${newsRssUrl.source}")
            cleanText
        }
    }
}
