package com.saadfauzi.mealguys.models

import com.google.gson.annotations.SerializedName

data class CountryModel(

	@field:SerializedName("meals")
	val meals: ArrayList<CountryItems>
)

data class CountryItems(

	@field:SerializedName("strArea")
	val strArea: String
)
