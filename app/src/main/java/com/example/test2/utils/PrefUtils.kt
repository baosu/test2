package com.example.test2.utils

import android.content.Context

const val PREF_FILE_KEY = "com.example.test2"
const val PREF_IS_FIRST_LAUNCH = "com.example.test2.IS_FIRST_LAUNCH"

inline fun <reified T : Any> Context.getPref(key: String): T? {
    val preferences = getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE)
    return when (T::class) {
        String::class -> preferences.getString(key, "") as? T
        Int::class -> preferences.getInt(key, 0) as? T
        Boolean::class -> preferences.getBoolean(key, false) as? T
        Float::class -> preferences.getFloat(key, 0f) as? T
        Long::class -> preferences.getLong(key, 0L) as? T
        else -> {
            return null
        }
    }
}

inline fun <reified T : Any> Context.putPref(key: String, data: T?) {
    val preferences = getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE)
    val editor = preferences.edit()
    when (T::class) {
        String::class -> editor.putString(key, data as String)
        Int::class -> editor.putInt(key, data as Int)
        Boolean::class -> editor.putBoolean(key, data as Boolean)
        Float::class -> editor.putFloat(key, data as Float)
        Long::class -> editor.putLong(key, data as Long)
        else -> {
        }
    }
    editor.commit()
}
