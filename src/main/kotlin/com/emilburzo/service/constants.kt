package com.emilburzo.service

/**
 * Created by emil on 07.12.2019.
 */
val NEWS_RSS_URLS = setOf(
    NewsRssUrl(
        source = "emaramures.ro",
        url = "https://www.emaramures.ro/feed/",
        spamSeparator = "[…]"
    ),
    NewsRssUrl(
        source = "jurnalmm.ro",
        url = "https://jurnalmm.ro/feed/",
        spamSeparator = "Oferta de nerefuzat"
    ),
    NewsRssUrl(
        source = "vasiledale.ro",
        url = "https://vasiledale.ro/feed/",
        spamSeparator = "[…]"
    ),
    NewsRssUrl(
        source = "ziarmm.ro",
        url = "https://ziarmm.ro/feed/",
        spamSeparator = " ... "
    ),
    NewsRssUrl(
        source = "actualmm.ro",
        url = "https://www.actualmm.ro/feed/",
        spamSeparator = "[…]"
    ),
    NewsRssUrl(
        source = "directmm.ro",
        url = "https://www.directmm.ro/feed/",
        spamSeparator = "[…]"
    )
)