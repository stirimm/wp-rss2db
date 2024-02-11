package com.emilburzo.service.http

import com.emilburzo.service.NewsRssUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

/**
 * Created by emil on 07.12.2019.
 */

val httpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .hostnameVerifier { _, _ -> true } // Disable hostname verification
    .build()

class Http {

    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun getContent(url: NewsRssUrl): String? = withContext(Dispatchers.IO) {
        logger.info("getting content for $url")

        val requestBuilder = Request.Builder().url(url.url)
        if (url.ip != null) {
            requestBuilder.addHeader("Host", url.source)
            requestBuilder.url(url.url.replace(url.source, url.ip))
        }

        val request = requestBuilder.build()
        val content = httpClient.newCall(request).execute().body?.string()

        logger.info("got content for $url")

        content
    }
}