package com.saadfauzi.mealguys.models

import com.google.gson.annotations.SerializedName

data class FilterMealModel(

	@field:SerializedName("meals")
	val meals: ArrayList<FilterItems>
)

data class FilterItems(

	@field:SerializedName("strMealThumb")
	val strMealThumb: String,

	@field:SerializedName("idMeal")
	val idMeal: String,

	@field:SerializedName("strMeal")
	val strMeal: String,
)
