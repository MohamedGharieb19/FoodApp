package com.gharieb.foodapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gharieb.foodapp.R
import com.gharieb.foodapp.adapters.CategoryAdapter
import com.gharieb.foodapp.databinding.FragmentCategoryBinding
import com.gharieb.foodapp.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCategoryMeals()
        onCategoryItemClick()

    }

    private fun setupRecyclerView(){
        categoryAdapter = CategoryAdapter()
        binding.categoriesRecyclerView.apply {
            layoutManager = GridLayoutManager(context,3, RecyclerView.VERTICAL,false)
            adapter = categoryAdapter
        }
    }

    private fun getCategoryMeals(){
        setupRecyclerView()
        viewModel.getCategoryOfMeals()
        viewModel.getCategoryMealLiveData.observe(viewLifecycleOwner, Observer {
            categoryAdapter.differ.submitList(it)
        })
    }

    private fun onCategoryItemClick(){
        categoryAdapter.onCategoryItemClick = { data ->
            val fragment = DetailsOfCategoryFragment()
            val bundle = Bundle()
            bundle.putString("mealCategory",data.strCategory)
            fragment.arguments = bundle
            findNavController().navigate(R.id.action_categoryFragment_to_detailsOfCategoryFragment,bundle)

        }
    }
}