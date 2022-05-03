package com.orxeira.tmdb_browser.di

import android.app.Application
import com.orxeira.tmdb_browser.initTestDI

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initTestDI()
    }
}
