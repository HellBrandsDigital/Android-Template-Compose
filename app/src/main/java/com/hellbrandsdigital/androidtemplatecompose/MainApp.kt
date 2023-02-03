package com.hellbrandsdigital.androidtemplatecompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApp : Application() {
    companion object {
        lateinit var instance: MainApp private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
