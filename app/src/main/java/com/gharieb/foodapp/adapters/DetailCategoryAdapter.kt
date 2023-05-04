package com.gharieb.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.gharieb.foodapp.data.Popular
import com.gharieb.foodapp.databinding.FavoriteItemBinding

class DetailCategoryAdapter() : RecyclerView.Adapter<DetailCategoryAdapter.ViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Popular>(){
        override fun areItemsTheSame(oldItem: Popular, newItem: Popular): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Popular, newItem: Popular): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    class ViewHolder(val binding: FavoriteItemBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FavoriteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        Glide.with(holder.itemView).load(data.strMealThumb).into(holder.binding.image)
        holder.binding.title.text = data.strMeal
    }
}