package com.gharieb.foodapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gharieb.foodapp.R
import com.gharieb.foodapp.adapters.CategoryAdapter
import com.gharieb.foodapp.adapters.PopularAdapter
import com.gharieb.foodapp.viewModel.HomeViewModel
import com.gharieb.foodapp.databinding.FragmentHomeBinding
import com.gharieb.foodapp.ui.MealActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var popularAdapter : PopularAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPopularMeals()
        getCategoryMeals()
        onPopularItemClick()
        onCategoryItemClick()
        underLinedText()

        binding.etSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

    }

    private fun onCategoryItemClick() {
        categoryAdapter.onCategoryItemClick = { data ->
            val fragment = DetailsOfCategoryFragment()
            val bundle = Bundle()
            bundle.putString("mealCategory",data.strCategory)
            fragment.arguments = bundle
            findNavController().navigate(R.id.action_homeFragment_to_detailsOfCategoryFragment , bundle)
        }
    }

    private fun onPopularItemClick() {
        popularAdapter.onPopularItemClick = { data ->
            val intent = Intent(context,MealActivity::class.java)
            intent.putExtra("mealId",data.idMeal)
            intent.putExtra("mealTitle",data.strMeal)
            intent.putExtra("mealThumb",data.strMealThumb)
            startActivity(intent)
        }
    }

    private fun getCategoryMeals() {
        setUpCategoryRecyclerView()
        homeViewModel.getCategoryOfMeals()
        homeViewModel.getCategoryMealLiveData.observe(viewLifecycleOwner){
            categoryAdapter.differ.submitList(it)
        }
    }

    private fun getPopularMeals() {
        setUpPopularRecyclerView()
        homeViewModel.getPopularMeals()
        homeViewModel.getPopularMealLiveData.observe(viewLifecycleOwner){
            popularAdapter.differ.submitList(it)
        }
    }

    private fun setUpCategoryRecyclerView(){
        categoryAdapter = CategoryAdapter()
        binding.categoriesRecyclerView.apply {
            adapter = categoryAdapter
        }
    }

    private fun setUpPopularRecyclerView(){
        popularAdapter = PopularAdapter()
        binding.popularRecyclerView.apply {
            adapter = popularAdapter
        }
    }

    private fun underLinedText(){
        val spannableString = SpannableString(binding.titleTextView.text)
        spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, 0)
        binding.titleTextView.text = spannableString
    }

}

