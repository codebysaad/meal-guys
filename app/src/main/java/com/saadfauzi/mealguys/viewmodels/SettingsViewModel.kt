package com.saadfauzi.mealguys.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.saadfauzi.mealguys.helpers.MyPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref: MyPreferences): ViewModel() {

    fun getStateDarkMode(): LiveData<Boolean> {
        return pref.getStateDarkMode().asLiveData()
    }

    fun saveStateDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            pref.saveStateDarkMode(isDarkMode)
        }
    }
}