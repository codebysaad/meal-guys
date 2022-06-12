package com.saadfauzi.mealguys.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saadfauzi.mealguys.R
import com.saadfauzi.mealguys.databinding.ItemsOurMealsBinding
import com.saadfauzi.mealguys.models.FilterItems

class AdapterListMeals(private val items: ArrayList<FilterItems>): RecyclerView.Adapter<AdapterListMeals.ViewHolder>() {

    private lateinit var onItemClickCallback: IOnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: IOnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(private val binding: ItemsOurMealsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: FilterItems) {
            binding.apply {
                tvMealsLabel.text = items.strMeal
                Glide.with(itemView.context)
                    .load(items.strMealThumb)
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
        fun onItemClicked(data: FilterItems)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}