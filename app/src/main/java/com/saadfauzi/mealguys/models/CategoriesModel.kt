package com.saadfauzi.mealguys.models

import com.google.gson.annotations.SerializedName

data class CategoriesModel(
	@field:SerializedName("categories")
	val meals: ArrayList<CategoriesItem>
)

data class CategoriesItem(
	@field:SerializedName("idCategory")
	val idCategory: String,
	@field:SerializedName("strCategory")
	val strCategory: String,
	@field:SerializedName("strCategoryThumb")
	val strCategoryThumb: String,
	@field:SerializedName("strCategoryDescription")
	val strCategoryDescription: String,
)
