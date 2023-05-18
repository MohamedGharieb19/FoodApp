package com.gharieb.foodapp.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gharieb.foodapp.adapters.SearchAdapter
import com.gharieb.foodapp.databinding.FragmentSearchBinding
import com.gharieb.foodapp.ui.MealActivity
import com.gharieb.foodapp.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter

    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMealsBySearch()
        openMeal()
        setEditTextOnFocusMode()

    }

    private fun setupRecyclerView(){
        searchAdapter = SearchAdapter()
        binding.searchRecyclerView.apply {
            adapter = searchAdapter
        }
    }

    private fun getMealsBySearch(){
        setupRecyclerView()

        var job: Job? = null

        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                showProgressBar()

                editable.let {
                    if (editable.toString().isNotEmpty()){
                        delay(1000)
                        viewModel.search(editable.toString())
                        hideProgressBar()

                    }else if (editable.toString().isEmpty()){
                        hideProgressBar()
                        searchAdapter.differ.submitList(null)

                    }

                }
            }
        }

        viewModel.searchMealLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()){
                showNoResults()
                searchAdapter.differ.submitList(null)
            }else if (it.isNotEmpty()) {
                hideNoResults()
                searchAdapter.differ.submitList(it)
            }

        })

    }

    private fun hideNoResults() {
        TODO("Not yet implemented")
    }

    private fun showNoResults() {
        TODO("Not yet implemented")
    }

    private fun openMeal(){
        searchAdapter.onMealItemClick = { data ->
            val intent = Intent(context, MealActivity::class.java)
            intent.putExtra("mealId",data.idMeal)
            intent.putExtra("mealTitle",data.strMeal)
            intent.putExtra("mealThumb",data.strMealThumb)
            startActivity(intent)
        }
    }

    private fun setEditTextOnFocusMode(){
        binding.etSearch.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etSearch, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideProgressBar() {
        binding.ProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.ProgressBar.visibility = View.VISIBLE
    }


}