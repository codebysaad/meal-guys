package com.saadfauzi.mealguys.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saadfauzi.mealguys.models.FilterItems
import com.saadfauzi.mealguys.models.FilterMealModel
import com.saadfauzi.mealguys.remote.ApiConfiguration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListMealsViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _mealsByCountry = MutableLiveData<ArrayList<FilterItems>>()
    val mealsByCountry: LiveData<ArrayList<FilterItems>> = _mealsByCountry

    private val _mealsByCategories = MutableLiveData<ArrayList<FilterItems>>()
    val mealsByCategories: LiveData<ArrayList<FilterItems>> = _mealsByCategories

    fun getListMealsByCountry(country: String) {
        _isLoading.value = true
        val client = ApiConfiguration.getApiService().getFilterByCountry(country)
        client.enqueue(object: Callback<FilterMealModel> {
            override fun onResponse(call: Call<FilterMealModel>, response: Response<FilterMealModel>) {
                _isLoading.value = false
                _mealsByCountry.value = response.body()?.meals
            }

            override fun onFailure(call: Call<FilterMealModel>, t: Throwable) {
                _isLoading.value = false
                _mealsByCountry.value = null
                Log.e("ListMealsViewModel", t.message.toString())
            }
        })
    }

    fun getListMealsByCategory(category: String) {
        _isLoading.value = true
        val client = ApiConfiguration.getApiService().getFilterByCategory(category)
        client.enqueue(object: Callback<FilterMealModel> {
            override fun onResponse(call: Call<FilterMealModel>, response: Response<FilterMealModel>) {
                _isLoading.value = false
                _mealsByCategories.value = response.body()?.meals
            }

            override fun onFailure(call: Call<FilterMealModel>, t: Throwable) {
                _isLoading.value = false
                _mealsByCategories.value = null
                Log.e("ListMealsViewModel", t.message.toString())
            }
        })
    }
}