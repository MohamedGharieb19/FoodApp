package com.gharieb.foodapp.adapters

import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.gharieb.foodapp.data.Meal
import com.gharieb.foodapp.databinding.FavoriteItemBinding

class FavoritesAdapter(): RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

    class ViewHolder(val binding: FavoriteItemBinding): RecyclerView.ViewHolder(binding.root) {

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