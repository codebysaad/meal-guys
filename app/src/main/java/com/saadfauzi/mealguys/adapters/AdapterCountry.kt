package com.saadfauzi.mealguys.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saadfauzi.mealguys.adapters.AdapterCountry.*
import com.saadfauzi.mealguys.databinding.ItemsRowLayoutBinding
import com.saadfauzi.mealguys.models.CountryItems

class AdapterCountry(private val items: ArrayList<CountryItems>): RecyclerView.Adapter<ViewHolder>() {

    private lateinit var onItemClickCallback: IOnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: IOnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(private val binding: ItemsRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(country: CountryItems) {
            binding.apply {
                tvCountry.text = country.strArea
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(items[position])
        }
    }

    interface IOnItemClickCallback {
        fun onItemClicked(data: CountryItems)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}