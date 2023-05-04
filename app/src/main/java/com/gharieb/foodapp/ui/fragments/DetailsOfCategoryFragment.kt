package com.gharieb.foodapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gharieb.foodapp.R
import com.gharieb.foodapp.adapters.DetailCategoryAdapter
import com.gharieb.foodapp.adapters.FavoritesAdapter
import com.gharieb.foodapp.databinding.FragmentDetailsOfCategoryBinding
import com.gharieb.foodapp.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsOfCategoryFragment : Fragment() {

    private lateinit var binding: FragmentDetailsOfCategoryBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var categoryName: String
    private lateinit var detailCategoryAdapter: DetailCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsOfCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        getCategoryMeals()
        getMeals()

    }

    private fun setupRecyclerView() {
        detailCategoryAdapter = DetailCategoryAdapter()
        binding.detailCategoryRecyclerView.apply {
            layoutManager = GridLayoutManager(context,2, RecyclerView.VERTICAL,false)
            adapter = detailCategoryAdapter
        }
    }

    private fun getMeals(){
        setupRecyclerView()

        lifecycleScope.launchWhenStarted {
            viewModel.getDetailOfCategoryMeal(categoryName)
            viewModel.getDetailCategoryMealLiveData.observe(viewLifecycleOwner, Observer {
                detailCategoryAdapter.differ.submitList(it)
            })
        }
    }

    private fun getCategoryMeals() {
        lifecycleScope.launch {
            viewModel.getDetailOfCategoryMeal(categoryName)
            viewModel.getDetailCategoryMealLiveData.observe(viewLifecycleOwner, Observer {
                for (i in it)
                    Log.d("testApp", i.strMeal)
            })
        }
    }

    private fun getData() {
        val data = arguments
        if (data != null) {
            categoryName = data.getString("mealCategory").toString()
        }
    }


}