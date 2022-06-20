package com.saadfauzi.mealguys.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MealsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addBookmarked(meals: MealsEntity)

    @Query("SELECT * FROM meals_fav WHERE bookmarked = 1")
    fun getAllBookmarkedMeals(): LiveData<List<MealsEntity>>

    @Query("DELETE FROM meals_fav WHERE idMeal = :id")
    fun deleteBookmarkedMeal(id: String)

    @Query("SELECT EXISTS(SELECT * FROM meals_fav WHERE idMeal = :id AND bookmarked = 1)")
    fun isBookmarked(id: String): LiveData<Boolean>
}