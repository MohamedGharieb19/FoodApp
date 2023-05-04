package com.gharieb.foodapp.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.gharieb.foodapp.R
import com.gharieb.foodapp.data.Meal
import com.gharieb.foodapp.databinding.ActivityMealBinding
import com.gharieb.foodapp.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding

    private lateinit var mealId: String
    private lateinit var mealTitle: String
    private lateinit var mealThumb: String
    private lateinit var videoLink: String

    private val homeViewModel: HomeViewModel by viewModels()
    private var favoriteMeal: Meal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMeal()
        setMeal(mealId)

        binding.favoriteButton.setOnClickListener {
            favoriteMeal?.let {meal ->
                try {
                    homeViewModel.upsert(meal)

                }catch (t:Throwable){
                    Log.d("testApp",t.message.toString())
                }
            }

        }

    }



    private fun setMeal(mealId: String) {
        homeViewModel.getMeal(mealId)
        homeViewModel.getMealLiveData.observe(this, Observer{
            favoriteMeal = it

            Glide.with(this).load(it.strMealThumb).into(binding.image)
            binding.categoryNameTextView.text = "Category: " +it.strCategory
            binding.locationTextView.text = "Area: " +it.strArea
            binding.instractionsDetailsTextView.text = it.strInstructions
            videoLink = it.strYoutube
            binding.videoButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoLink))
                startActivity(intent)
            }
        })
    }

    private fun getMeal() {
        val intent = intent
        mealId = intent.getStringExtra("mealId").toString()
        mealTitle = intent.getStringExtra("mealTitle").toString()
        mealThumb = intent.getStringExtra("mealThumb").toString()

        Glide.with(applicationContext).load(mealThumb).into(binding.image)
        binding.instractionsTextView.text = mealTitle

    }
}