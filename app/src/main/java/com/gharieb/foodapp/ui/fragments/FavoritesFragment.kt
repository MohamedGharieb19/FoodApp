package com.gharieb.foodapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gharieb.foodapp.adapters.FavoritesAdapter
import com.gharieb.foodapp.databinding.FragmentFavoritesBinding
import com.gharieb.foodapp.ui.MealActivity
import com.gharieb.foodapp.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var favoritesAdapter: FavoritesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFavorites()
        openMeal()
        deleteFavorite()

    }

    fun setupRecyclerView(){
        favoritesAdapter = FavoritesAdapter()
        binding.favoriteRecyclerView.apply {
            adapter = favoritesAdapter
        }
    }

    private fun getFavorites(){
        setupRecyclerView()
        viewModel.getFavoritesMeal().observe(viewLifecycleOwner, Observer {meals ->
            favoritesAdapter.differ.submitList(meals)

        })
    }

    private fun openMeal(){
        favoritesAdapter.onMealItemClick = { data ->
            val intent = Intent(context, MealActivity::class.java)
            intent.putExtra("mealId",data.idMeal)
            intent.putExtra("mealTitle",data.strMeal)
            intent.putExtra("mealThumb",data.strMealThumb)
            startActivity(intent)
        }
    }

    private fun deleteFavorite(){
        favoritesAdapter.onIconItemClick = {data ->
            viewModel.delete(data)
            view?.let {
                Snackbar.make(it,"Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.upsert(data)
                    }
                    show()
                }
            }
        }
    }


}