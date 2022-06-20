package com.saadfauzi.mealguys.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saadfauzi.mealguys.database.MealsDao
import com.saadfauzi.mealguys.helpers.Injection
import com.saadfauzi.mealguys.repository.MealsRepository

class DetailViewModelFactory(private val repository: MealsRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailMealViewModel::class.java)){
            return DetailMealViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: DetailViewModelFactory? = null
        fun getInstance(context: Context): DetailViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: DetailViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}