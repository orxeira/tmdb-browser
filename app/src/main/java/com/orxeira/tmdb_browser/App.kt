package com.orxeira.tmdb_browser

import android.app.Application

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        initDi()
    }
}