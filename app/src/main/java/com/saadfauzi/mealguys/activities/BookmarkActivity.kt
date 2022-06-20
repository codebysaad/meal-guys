package com.saadfauzi.mealguys.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.saadfauzi.mealguys.adapters.AdapterBookmarkMeals
import com.saadfauzi.mealguys.database.MealsEntity
import com.saadfauzi.mealguys.databinding.ActivityBookmarkBinding
import com.saadfauzi.mealguys.viewmodels.DetailMealViewModel
import com.saadfauzi.mealguys.viewmodels.DetailViewModelFactory

class BookmarkActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBookmarkBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: DetailMealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = obtainViewModel()
        viewModel.getAllBookmarkedMeals().observe(this) {
            if (it.isEmpty()) {
                binding.apply {
                    rvListBookmark.visibility = View.GONE
                    icNotFoundData.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    rvListBookmark.visibility = View.VISIBLE
                    icNotFoundData.visibility = View.GONE
                }
                showRecyclerView(it)
            }
        }
    }

    private fun obtainViewModel(): DetailMealViewModel {
        val factory = DetailViewModelFactory.getInstance(application)
        return ViewModelProvider(this, factory)[DetailMealViewModel::class.java]
    }

    private fun showRecyclerView(list: List<MealsEntity>) {
        binding.rvListBookmark.layoutManager = GridLayoutManager(this, 2)
        val adapterListMeals = AdapterBookmarkMeals(list)
        binding.rvListBookmark.adapter = adapterListMeals
        adapterListMeals.setOnItemClickCallback(object : AdapterBookmarkMeals.IOnItemClickCallback {
            override fun onItemClicked(data: MealsEntity) {
                Toast.makeText(this@BookmarkActivity, data.strMeal, Toast.LENGTH_SHORT).show()
                val intent = Intent(this@BookmarkActivity, DetailMealActivity::class.java)
                intent.putExtra(DetailMealActivity.EXTRA_ID_MEAL, data.idMeal)
                startActivity(intent)
            }
        })
    }
}