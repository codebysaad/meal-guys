package com.saadfauzi.mealguys.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.saadfauzi.mealguys.database.MealsDao
import com.saadfauzi.mealguys.database.MealsEntity
import com.saadfauzi.mealguys.helpers.AppExecutors
import com.saadfauzi.mealguys.remote.ApiServices
import com.saadfauzi.mealguys.helpers.Result
import com.saadfauzi.mealguys.models.DetailMealModel
import com.saadfauzi.mealguys.models.DetailMealsItem
import com.saadfauzi.mealguys.models.MealsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealsRepository private constructor(
    private val apiServices: ApiServices,
    private val dao: MealsDao,
    private val appExecutors: AppExecutors
){
    private val result = MediatorLiveData<Result<DetailMealsItem>>()

    fun getDetailsMeal(idMeal: String): LiveData<Result<MealsEntity>> {
        result.value = Result.Loading
        val client = apiServices.getMealDetails(idMeal)
        client.enqueue(object: Callback<DetailMealModel> {
            override fun onResponse(
                call: Call<DetailMealModel>,
                response: Response<DetailMealModel>
            ) {
                if (response.isSuccessful) {
                    result.value = Result.Success(response.body()?.meals) as Result<DetailMealsItem>
                }
            }

            override fun onFailure(call: Call<DetailMealModel>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        return result as LiveData<Result<MealsEntity>>
    }

    fun getAllBookmarkedMeals(): LiveData<List<MealsEntity>> {
        return dao.getAllBookmarkedMeals()
    }

    fun mealIsBookmarked(idMeal: String): LiveData<Boolean> {
        return dao.isBookmarked(idMeal)
    }

    fun deleteBookmarkMeal(idMeal: String) {
        appExecutors.diskIO.execute {
            dao.deleteBookmarkedMeal(idMeal)
        }
    }

    fun setBookmarkMeal(mealsEntity: MealsEntity, bookmark: Boolean){
        appExecutors.diskIO.execute {
            mealsEntity.isBookmarked = bookmark
            dao.addBookmarked(mealsEntity)
        }
    }

    companion object {
        @Volatile
        private var instance: MealsRepository? = null
        fun getInstance(
            apiServices: ApiServices,
            dao: MealsDao,
            appExecutors: AppExecutors
        ): MealsRepository =
            instance ?: synchronized(this) {
                instance ?: MealsRepository(apiServices, dao, appExecutors)
            }.also { instance = it }
    }
}