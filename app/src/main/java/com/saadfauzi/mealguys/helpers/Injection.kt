package com.saadfauzi.mealguys.helpers

import android.content.Context
import com.saadfauzi.mealguys.database.MealsDatabase
import com.saadfauzi.mealguys.remote.ApiConfiguration
import com.saadfauzi.mealguys.repository.MealsRepository

object Injection {
    fun provideRepository(context: Context): MealsRepository {
        val apiServices = ApiConfiguration.getApiService()
        val database = MealsDatabase.getInstance(context)
        val dao = database.mealsDao()
        val appExecutors = AppExecutors()
        return MealsRepository.getInstance(apiServices, dao, appExecutors)
    }
}