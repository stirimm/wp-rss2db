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
        spamSeparator = " Profită acum "
    ),
    NewsRssUrl(
        source = "vasiledale.ro",
        url = "https://vasiledale.ro/feed/",
        spamSeparator = "[…]"
    ),
    NewsRssUrl(
        source = "ziarmm.ro",
        url = "https://ziarmm.ro/feed/",
        spamSeparator = " The post "
    ),
    NewsRssUrl(
        source = "actualmm.ro",
        url = "https://www.actualmm.ro/feed/",
        spamSeparator = " The post "
    ),
    NewsRssUrl(
        source = "directmm.ro",
        url = "https://www.directmm.ro/feed/",
        spamSeparator = "[…]"
    ),
    NewsRssUrl(
        source = "2mnews.ro",
        url = "https://2mnews.ro/feed/",
        spamSeparator = "[…]",
        ip = "95.217.227.123"
    ),
    NewsRssUrl(
        source = "graiul.ro",
        url = "https://graiul.ro/feed/",
        spamSeparator = " Post-ul "
    ),
    NewsRssUrl(
        source = "ziaruldemaramures.ro",
        url = "https://ziaruldemaramures.ro/feed//",
        spamSeparator = " The post "
    ),
)
