package com.saadfauzi.mealguys.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saadfauzi.mealguys.R
import com.saadfauzi.mealguys.databinding.ItemsOurMealsBinding
import com.saadfauzi.mealguys.databinding.ItemsRowLayoutBinding
import com.saadfauzi.mealguys.models.CategoriesItem
import com.saadfauzi.mealguys.models.CountryItems

class AdapterCategories(private val items: ArrayList<CategoriesItem>): RecyclerView.Adapter<AdapterCategories.ViewHolder>() {

    private lateinit var onItemClickCallback: IOnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: IOnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(private val binding: ItemsOurMealsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoriesItem) {
            binding.apply {
                tvMealsLabel.text = category.strCategory
                Glide.with(itemView.context)
                    .load(category.strCategoryThumb)
                    .placeholder(R.drawable.ic_place_holder)
                    .into(icCategories)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsOurMealsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(items[position])
        }
    }

    interface IOnItemClickCallback {
        fun onItemClicked(data: CategoriesItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}