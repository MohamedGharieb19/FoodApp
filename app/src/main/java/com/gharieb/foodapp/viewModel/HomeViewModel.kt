package com.gharieb.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gharieb.foodapp.Repository.HomeRepository
import com.gharieb.foodapp.data.Category
import com.gharieb.foodapp.data.Meal
import com.gharieb.foodapp.data.Popular
import com.gharieb.foodapp.data.PopularList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val homeRepository: HomeRepository
): ViewModel() {

    private val _getRandomMealMutableLiveData = MutableLiveData<Meal>()
    val getRandomMealLiveData: LiveData<Meal> = _getRandomMealMutableLiveData

    private val _getPopularMealMutableLiveData = MutableLiveData<List<Popular>>()
    val getPopularMealLiveData: LiveData<List<Popular>> = _getPopularMealMutableLiveData

    private val _getCategoryMealMutableLiveData = MutableLiveData<List<Category>>()
    val getCategoryMealLiveData: LiveData<List<Category>> = _getCategoryMealMutableLiveData

    private val _getDetailCategoryMealMutableLiveData = MutableLiveData<List<Popular>>()
    val getDetailCategoryMealLiveData: LiveData<List<Popular>> = _getDetailCategoryMealMutableLiveData

    private val _getMealMutableLiveData = MutableLiveData<Meal>()
    val getMealLiveData: LiveData<Meal> = _getMealMutableLiveData

    private val _searchMutableLiveData = MutableLiveData<List<Meal>>()
    val searchMealLiveData: LiveData<List<Meal>> = _searchMutableLiveData


    fun getRandomMeal(){
        viewModelScope.launch {

            try {
                val response = homeRepository.getRandomMeal()

                response.body()!!.meals.let {
                    _getRandomMealMutableLiveData.postValue(it[0])
                }
            }catch (t:Throwable){
                Log.d("testApp",t.message.toString()+ " Error get random meal")
            }


        }
    }

    fun getPopularMeals(){
        viewModelScope.launch {
            try {
                val response = homeRepository.getPopularMeals("Canadian")

                response.body()!!.meals.let {
                    _getPopularMealMutableLiveData.postValue(it)
                }
            }catch (t:Throwable){
                Log.d("testApp",t.message.toString()+ " Error get popular meal")
            }
        }
    }

    fun getCategoryOfMeals(){
        viewModelScope.launch {
            try {
                val response = homeRepository.getCategoryOfMeals()

                response.body()!!.categories.let {
                    _getCategoryMealMutableLiveData.postValue(it)
                }
            }catch (t:Throwable){
                Log.d("testApp",t.message.toString()+ " Error get category meal")
            }
        }
    }

    fun getDetailOfCategoryMeal(categoryName: String){
        viewModelScope.launch {
            try {
                val response = homeRepository.getDetailOfCategoryMeals(categoryName)

                response.body()!!.meals.let {
                    _getDetailCategoryMealMutableLiveData.postValue(it)
                }

            }catch (t:Throwable){
                Log.d("testApp",t.message.toString()+ " Error get detail category meal")
            }
        }
    }

    fun getMeal(mealId: String){
        viewModelScope.launch {
            try {
                val response = homeRepository.getMeal(mealId)

                response.body()!!.meals.let {
                    _getMealMutableLiveData.value = response.body()!!.meals[0]
                }
            }catch (t:Throwable){
                Log.d("testApp",t.message.toString()+ " Error getmeal")
            }

        }
    }

    fun search(searchQuery:String){
        viewModelScope.launch {
            try {
                val response = homeRepository.search(searchQuery)

                response.body()!!.meals.let {
                    _searchMutableLiveData.postValue(it)
                }
            }catch (t:Throwable){
                Log.d("testApp",t.message.toString()+ " Error search")
            }
        }
    }

    fun upsert(meal: Meal) = viewModelScope.launch {
        homeRepository.upsert(meal)
    }

    fun delete(meal: Meal) = viewModelScope.launch {
        homeRepository.delete(meal)
    }

    fun getFavoritesMeal() = homeRepository.getFavoritesMeal

}