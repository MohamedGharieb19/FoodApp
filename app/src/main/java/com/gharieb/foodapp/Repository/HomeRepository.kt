package com.gharieb.foodapp.Repository

import android.util.Log
import com.gharieb.foodapp.api.MealApi
import com.gharieb.foodapp.data.CategoryList
import com.gharieb.foodapp.data.Meal
import com.gharieb.foodapp.data.MealList
import com.gharieb.foodapp.data.PopularList
import com.gharieb.foodapp.database.MealDataBase
import retrofit2.Response
import javax.inject.Inject


class HomeRepository @Inject constructor(
    private val mealApi: MealApi,
    private val MealDatabase: MealDataBase
) {
    private val dataBase = MealDatabase.mealDao()
    val getFavoritesMeal = dataBase.getFavorites()

    suspend fun getRandomMeal(): Response<MealList> {
        val response = mealApi.getRandomMeal()
        if (response.isSuccessful){
            Log.d("TestApp","Success to connected: ${response.code()}")
        }else {
            Log.d("TestApp","Failed to connected: ${response.code()}")
        }
        return response
    }

    suspend fun getPopularMeals(categoryName: String): Response<PopularList>{
        val response = mealApi.getPopularMeals(categoryName)
        if (response.isSuccessful){
            Log.d("TestApp","Success to connected to popular meal: ${response.code()}")
        }else {
            Log.d("TestApp","Failed to connected to popular meal: ${response.code()}")
        }
        return response
    }

    suspend fun getCategoryOfMeals(): Response<CategoryList>{
        val response = mealApi.getCategoryOfMeals()
        if (response.isSuccessful){
            Log.d("TestApp","Success to connected to Category of meals: ${response.code()}")
        }else {
            Log.d("TestApp","Failed to connected to Category of meals: ${response.code()}")
        }
        return response
    }

    suspend fun getMeal(mealId: String): Response<MealList>{
        val response = mealApi.getMeal(mealId)
        if (response.isSuccessful){
            Log.d("TestApp","Success to connected to meal details: ${response.code()}")
        }else {
            Log.d("TestApp","Failed to connected to meal details: ${response.code()}")
        }
        return response
    }

    suspend fun getDetailOfCategoryMeals(categoryName: String): Response<PopularList>{
        val response = mealApi.getDetailCategoryMeals(categoryName)
        if (response.isSuccessful){
            Log.d("TestApp","Success to connected to detail category meal: ${response.code()}")
        }else {
            Log.d("TestApp","Failed to connected to detail category meal: ${response.code()}")
        }
        return response
    }

    suspend fun upsert(meal: Meal){
        dataBase.upsert(meal)
    }

    suspend fun delete(meal: Meal){
        dataBase.delete(meal)
    }




}