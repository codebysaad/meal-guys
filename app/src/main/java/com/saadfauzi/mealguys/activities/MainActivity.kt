package com.saadfauzi.mealguys.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.saadfauzi.mealguys.adapters.AdapterCategories
import com.saadfauzi.mealguys.adapters.AdapterCountry
import com.saadfauzi.mealguys.databinding.ActivityMainBinding
import com.saadfauzi.mealguys.models.CategoriesItem
import com.saadfauzi.mealguys.models.CountryItems
import com.saadfauzi.mealguys.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvCountry.setHasFixedSize(true)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.responseData.observe(this) {country ->
            if (country != null) {
                showCountryMenus(country.meals)
            }
        }

        viewModel.responseCategories.observe(this) {categories ->
            if (categories != null) {
                showCategoryMenus(categories)
            }
        }

    }

    private fun showCountryMenus(list: ArrayList<CountryItems>) {
        binding.rvCountry.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapterUser = AdapterCountry(list)
        binding.rvCountry.adapter = adapterUser
        adapterUser.setOnItemClickCallback(object : AdapterCountry.IOnItemClickCallback {
            override fun onItemClicked(data: CountryItems) {
                Toast.makeText(this@MainActivity, data.strArea, Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, ListMealsActivity::class.java)
                intent.putExtra(ListMealsActivity.EXTRA_FILTER_BY, data.strArea)
                intent.putExtra(ListMealsActivity.EXTRA_MODE, "country")
                startActivity(intent)
            }
        })
    }

    private fun showCategoryMenus(list: ArrayList<CategoriesItem>) {
        binding.rvMealsCategory.layoutManager = GridLayoutManager(this, 2)
        val adapterCategories = AdapterCategories(list)
        binding.rvMealsCategory.adapter = adapterCategories
        adapterCategories.setOnItemClickCallback(object : AdapterCategories.IOnItemClickCallback {
            override fun onItemClicked(data: CategoriesItem) {
                Toast.makeText(this@MainActivity, data.strCategory, Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, ListMealsActivity::class.java)
                intent.putExtra(ListMealsActivity.EXTRA_FILTER_BY, data.strCategory)
                intent.putExtra(ListMealsActivity.EXTRA_MODE, "category")
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbMain.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}