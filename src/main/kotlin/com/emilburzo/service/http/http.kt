package com.emilburzo.service.http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory

/**
 * Created by emil on 07.12.2019.
 */

val httpClient = OkHttpClient()

class Http {

    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun getContent(url: String): String? = withContext(Dispatchers.IO) {
        logger.info("getting content for $url")

        val request = Request.Builder().url(url).build()
        val content = httpClient.newCall(request).execute().body?.string()

        logger.info("got content for $url")

        content
    }
}