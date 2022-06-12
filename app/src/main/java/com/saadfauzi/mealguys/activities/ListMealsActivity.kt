package com.saadfauzi.mealguys.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.saadfauzi.mealguys.adapters.AdapterListMeals
import com.saadfauzi.mealguys.databinding.ActivityListMealsBinding
import com.saadfauzi.mealguys.models.FilterItems
import com.saadfauzi.mealguys.viewmodels.ListMealsViewModel

class ListMealsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityListMealsBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<ListMealsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val getMode = intent.getStringExtra(EXTRA_MODE)
        val getFilterBy = intent.getStringExtra(EXTRA_FILTER_BY)

        when(getMode) {
            "country" -> {
                if (getFilterBy != null) {
                    viewModel.getListMealsByCountry(getFilterBy)
                    viewModel.mealsByCountry.observe(this) {
                        showRecyclerView(it)
                    }
                }
            }
            "category" -> {
                if (getFilterBy != null) {
                    viewModel.getListMealsByCategory(getFilterBy)
                    viewModel.mealsByCategories.observe(this) {
                        showRecyclerView(it)
                    }
                }
            }
        }
    }

    private fun showRecyclerView(list: ArrayList<FilterItems>) {
        binding.rvListMeals.layoutManager = GridLayoutManager(this, 2)
        val adapterListMeals = AdapterListMeals(list)
        binding.rvListMeals.adapter = adapterListMeals
        adapterListMeals.setOnItemClickCallback(object : AdapterListMeals.IOnItemClickCallback {
            override fun onItemClicked(data: FilterItems) {
                Toast.makeText(this@ListMealsActivity, data.strMeal, Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ListMealsActivity, DetailMealActivity::class.java)
                intent.putExtra(DetailMealActivity.EXTRA_ID_MEAL, data.idMeal)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbListMeals.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_FILTER_BY = "filter_meals_by"
        const val EXTRA_MODE = "country_or_category"
    }
}