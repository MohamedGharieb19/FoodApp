package com.gharieb.foodapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gharieb.foodapp.data.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealDataBase: RoomDatabase() {
    abstract fun mealDao(): MealDao
}