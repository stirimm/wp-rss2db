package com.emilburzo.main

import com.emilburzo.db.Db
import com.emilburzo.service.WpRss2Db
import com.emilburzo.service.http.Http
import com.emilburzo.service.rss.Rss

fun main(args: Array<String>) {
    WpRss2Db(
        db = Db(),
        http = Http(),
        rss = Rss()
    ).run()
}

