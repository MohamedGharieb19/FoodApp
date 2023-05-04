package com.gharieb.foodapp.ui.fragments

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
import com.gharieb.foodapp.viewModel.HomeViewModel
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
    }

    fun setupRecyclerView(){
        favoritesAdapter = FavoritesAdapter()
        binding.favoriteRecyclerView.apply {
            layoutManager = GridLayoutManager(context,2, RecyclerView.VERTICAL,false)
            adapter = favoritesAdapter
        }
    }

    private fun getFavorites(){
        setupRecyclerView()

        viewModel.getFavoritesMeal().observe(viewLifecycleOwner, Observer {meals ->
            favoritesAdapter.differ.submitList(meals)

        })


    }


}