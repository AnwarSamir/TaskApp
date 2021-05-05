package com.codestomp.task

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate


class AppController : Application() {

    companion object {
        lateinit var appInstance: AppController
            private set


        @Synchronized
        fun getInstance(): Context? {
            return appInstance
        }


    }

    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate()
        appInstance = this

    }




}