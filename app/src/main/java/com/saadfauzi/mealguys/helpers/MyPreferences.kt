package com.saadfauzi.mealguys.helpers

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyPreferences private constructor(private val datastore: DataStore<Preferences>) {

    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")

    fun getStateDarkMode(): Flow<Boolean> {
        return datastore.data.map { preferences ->
            preferences[DARK_MODE_KEY] ?: false
        }
    }

    suspend fun saveStateDarkMode(isDarkMode: Boolean) {
        datastore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isDarkMode
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MyPreferences? = null

        fun getInstance(datastore: DataStore<Preferences>): MyPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = MyPreferences(datastore)
                INSTANCE = instance
                return instance
            }
        }
    }
}