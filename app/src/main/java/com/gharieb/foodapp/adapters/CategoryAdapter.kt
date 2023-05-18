package com.gharieb.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gharieb.foodapp.data.Category
import com.gharieb.foodapp.databinding.CategoryItemBinding
import com.gharieb.foodapp.databinding.PopularItemBinding

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    lateinit var onCategoryItemClick: ((Category)  -> Unit )

    private val diffUtil = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

    class ViewHolder(val binding: PopularItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        Glide.with(holder.itemView).load(data.strCategoryThumb).into(holder.binding.image)
        holder.binding.title.text = data.strCategory

        holder.itemView.setOnClickListener {
            onCategoryItemClick.invoke(data)
        }
    }
}