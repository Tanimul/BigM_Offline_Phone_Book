package com.example.bigm_offlinephonebook.utils.extentions

import android.content.Context
import android.content.Intent
import com.example.bigm_offlinephonebook.PhoneBookApp
import com.example.bigm_offlinephonebook.utils.SharedPrefUtils

inline fun <reified T : Any> Context.launchActivity() {
    startActivity(Intent(this, T::class.java))
}

fun getSharedPrefInstance(): SharedPrefUtils {
    return if (PhoneBookApp.sharedPrefUtils == null) {
        PhoneBookApp.sharedPrefUtils = SharedPrefUtils()
        PhoneBookApp.sharedPrefUtils!!
    } else {
        PhoneBookApp.sharedPrefUtils!!
    }
}