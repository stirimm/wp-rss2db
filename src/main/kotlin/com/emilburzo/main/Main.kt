package com.emilburzo.main

import com.emilburzo.db.Db
import com.emilburzo.service.WpRss2Db

fun main(args: Array<String>) {
    WpRss2Db(Db()).run()
}

