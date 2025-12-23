package com.emilburzo.service

import java.util.*

/**
 * Created by emil on 01.12.2019.
 */
data class News(
    val published: Date,
    val title: String,
    val description: String,
    val url: String,
    val source: String
)

data class NewsRssUrl(
    val source: String,
    val url: String,
    val spamSeparator: String
)