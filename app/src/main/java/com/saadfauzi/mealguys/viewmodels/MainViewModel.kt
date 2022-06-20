package com.saadfauzi.mealguys.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saadfauzi.mealguys.models.CategoriesItem
import com.saadfauzi.mealguys.models.CategoriesModel
import com.saadfauzi.mealguys.models.CountryModel
import com.saadfauzi.mealguys.remote.ApiConfiguration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _responseCountry = MutableLiveData<CountryModel>()
    val responseData: LiveData<CountryModel> = _responseCountry

    private val _responseCategories = MutableLiveData<ArrayList<CategoriesItem>>()
    val responseCategories: LiveData<ArrayList<CategoriesItem>> = _responseCategories

    init {
        getAllCountry()
        getAllCategories()
    }

    private fun getAllCountry() {
        _isLoading.value = true
        val client = ApiConfiguration.getApiService().getCountriesList("list")
        client.enqueue(object: Callback<CountryModel> {
            override fun onResponse(call: Call<CountryModel>, response: Response<CountryModel>) {
                _isLoading.value = false
                _responseCountry.value = response.body()
            }

            override fun onFailure(call: Call<CountryModel>, t: Throwable) {
                _isLoading.value = false
                _responseCountry.value = null
                Log.e("MainViewModel", t.message.toString())
            }
        })
    }

    private fun getAllCategories() {
        _isLoading.value = true
        val client = ApiConfiguration.getApiService().getAllCategories()
        client.enqueue(object: Callback<CategoriesModel> {
            override fun onResponse(call: Call<CategoriesModel>, response: Response<CategoriesModel>) {
                _isLoading.value = false
                _responseCategories.value = response.body()?.meals
            }

            override fun onFailure(call: Call<CategoriesModel>, t: Throwable) {
                _isLoading.value = false
                _responseCategories.value = null
                Log.e("MainViewModel", t.message.toString())
            }
        })
    }
}