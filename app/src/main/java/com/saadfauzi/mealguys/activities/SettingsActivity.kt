package com.saadfauzi.mealguys.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.saadfauzi.mealguys.databinding.ActivitySettingsBinding
import com.saadfauzi.mealguys.helpers.MyPreferences
import com.saadfauzi.mealguys.viewmodels.SettingsViewModel
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pref = MyPreferences.getInstance(dataStore)
        viewModel = SettingsViewModel(pref)

        viewModel.getStateDarkMode().observe(this) { isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.swDarkMode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.swDarkMode.isChecked = false
            }
        }

        binding.apply {
            swDarkMode.setOnCheckedChangeListener { _, isChecked ->
                viewModel.saveStateDarkMode(isChecked)
            }
            btnChangeLanguage.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            val currentLanguage = Locale.getDefault().displayLanguage
            tvCurrentLanguage.text = currentLanguage
        }
    }
}