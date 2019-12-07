package com.emilburzo.service

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

data class NewsRssUrl(
    val source: String,
    val url: String
)