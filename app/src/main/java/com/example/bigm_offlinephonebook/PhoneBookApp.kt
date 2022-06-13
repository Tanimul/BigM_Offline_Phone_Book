package com.example.bigm_offlinephonebook

import android.app.Application
import com.example.bigm_offlinephonebook.utils.SharedPrefUtils

class PhoneBookApp : Application() {

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object {
        private lateinit var instance: PhoneBookApp
        var sharedPrefUtils: SharedPrefUtils? = null
        fun getInstance(): PhoneBookApp {
            return instance
        }

    }

}