package com.medicine.database.kotlinmedicine

import android.app.Application

/**
 * Created by tosya on 16.02.18.
 */

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}