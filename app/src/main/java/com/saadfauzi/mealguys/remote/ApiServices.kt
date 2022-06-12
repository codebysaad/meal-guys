package com.saadfauzi.mealguys.remote

import com.saadfauzi.mealguys.helpers.Utilities
import com.saadfauzi.mealguys.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET(Utilities.LIST)
    fun getCategoriesList(
        @Query("c") country: String
    ): Call<CategoryModel>

    @GET(Utilities.LIST)
    fun getCountriesList(
        @Query("a") country: String
    ): Call<CountryModel>

    @GET(Utilities.FILTER_MEALS)
    fun getFilterByCategory(
        @Query("c") country: String
    ): Call<FilterMealModel>

    @GET(Utilities.FILTER_MEALS)
    fun getFilterByCountry(
        @Query("a") country: String
    ): Call<FilterMealModel>

    @GET(Utilities.ALL_CATEGORIES)
    fun getAllCategories(): Call<CategoriesModel>

    @GET(Utilities.DETAILS_BY_ID)
    fun getMealDetails(
        @Query("i") idMeal: String
    ): Call<DetailMealModel>
}