package com.hellbrandsdigital.utils

import android.content.Context
import androidx.preference.PreferenceManager

class SettingsManager(appContext: Context) {

    private val sharedPref =
        PreferenceManager.getDefaultSharedPreferences(appContext)

    /**
     * @param key The key of the preference
     * @return the string value associated to the key, an empty string "" or null in case of an error
     */
    fun getFlagString(key: String): String? = sharedPref.getString(key, "")

    /**
     * @param key The key of the preference
     * @param value The value that should be associated with the key
     */
    fun setFlagString(key: String, value: String): Unit =
        sharedPref.edit().putString(key, value).apply()

    /**
     * @param key The key of the preference
     * @return the int value associated to the key, -1 or null in case of an error
     */
    fun getFlagInt(key: String): Int? = sharedPref.getString(key, "-1")?.toIntOrNull()

    /**
     * @param key The key of the preference
     * @param value The value that should be associated with the key
     */
    fun setFlagInt(key: String, value: Int): Unit =
        sharedPref.edit().putString(key, value.toString()).apply()

}
