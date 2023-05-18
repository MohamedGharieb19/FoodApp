package com.gharieb.foodapp.api

import com.gharieb.foodapp.data.CategoryList
import com.gharieb.foodapp.data.Meal
import com.gharieb.foodapp.data.MealList
import com.gharieb.foodapp.data.PopularList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    suspend fun getRandomMeal(): Response<MealList>

    @GET("filter.php")
    suspend fun getPopularMeals(
        @Query("a") categoryName: String
    ): Response<PopularList>

    @GET("categories.php")
    suspend fun getCategoryOfMeals():Response<CategoryList>

    @GET("lookup.php")
    suspend fun getMeal(
        @Query("i") mealId: String
    ) : Response<MealList>

    @GET("filter.php")
    suspend fun getDetailCategoryMeals(
        @Query("c") categoryName: String
    ): Response<PopularList>

    @GET("search.php")
    suspend fun search(
        @Query("s") searchQuery: String
    ): Response<MealList>

}