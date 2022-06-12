package com.saadfauzi.mealguys.models

import com.google.gson.annotations.SerializedName

data class CategoryModel(

	@field:SerializedName("meals")
	val meals: List<MealsItem>
)

data class MealsItem(

	@field:SerializedName("strCategory")
	val strCategory: String
)
