package com.saadfauzi.mealguys.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saadfauzi.mealguys.R
import com.saadfauzi.mealguys.adapters.AdapterCountry.*
import com.saadfauzi.mealguys.databinding.ItemsRowLayoutBinding
import com.saadfauzi.mealguys.models.CountryItems

class AdapterCountry(private val items: ArrayList<CountryItems>): RecyclerView.Adapter<ViewHolder>() {

    private lateinit var onItemClickCallback: IOnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: IOnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(private val binding: ItemsRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(country: CountryItems, flags: Int) {
            binding.apply {
                tvCountry.text = country.strArea
                icCountry.setImageResource(flags)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flags = arrayOf(R.drawable.american, R.drawable.british, R.drawable.canadian, R.drawable.chinese, R.drawable.croatian, R.drawable.dutch, R.drawable.egyptian, R.drawable.french, R.drawable.greek,
            R.drawable.indian, R.drawable.irish, R.drawable.italian, R.drawable.jamaican, R.drawable.japanese, R.drawable.kenyan, R.drawable.malaysian, R.drawable.mexican, R.drawable.moroccan, R.drawable.polish,
            R.drawable.portuguese, R.drawable.russian, R.drawable.spanish, R.drawable.thai, R.drawable.tunisian, R.drawable.turkish, R.drawable.unknown, R.drawable.vietnamese)

        holder.bind(items[position], flags[position])

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