package com.example.myapplication.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import javax.inject.Inject

const val PREFS_NAME = "Prefs_name"
const val PREF_TAG = "Prefs_tag"

class ShoppingPreferences @Inject constructor(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

    fun getCity(): String {
        return prefs?.getString(PREF_TAG, null) ?: ""
    }

    fun setCity(city: String) {
        prefs.edit()
            .putString(PREF_TAG, city)
            .apply()
    }
}