package com.saadfauzi.mealguys.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "meals_fav")
class MealsEntity(

    @PrimaryKey
    @ColumnInfo(name = "idMeal")
    val idMeal: String,

    @ColumnInfo(name = "strMealThumb")
    val strMealThumb: String?,

    @ColumnInfo(name = "strMeal")
    val strMeal: String?,

    @ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean

) : Parcelable
