package com.saadfauzi.mealguys.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saadfauzi.mealguys.models.DetailMealModel
import com.saadfauzi.mealguys.models.FilterMealModel
import com.saadfauzi.mealguys.remote.ApiConfiguration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMealViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _mealDetails = MutableLiveData<DetailMealModel>()
    val mealDetails: LiveData<DetailMealModel> = _mealDetails

    fun getMealDetails(idMeal: String) {
        _isLoading.value = true
        val client = ApiConfiguration.getApiService().getMealDetails(idMeal)
        client.enqueue(object: Callback<DetailMealModel> {
            override fun onResponse(call: Call<DetailMealModel>, response: Response<DetailMealModel>) {
                _isLoading.value = false
                _mealDetails.value = response.body()
            }

            override fun onFailure(call: Call<DetailMealModel>, t: Throwable) {
                _isLoading.value = false
                _mealDetails.value = null
                Log.e("ListMealsViewModel", t.message.toString())
            }
        })
    }
}