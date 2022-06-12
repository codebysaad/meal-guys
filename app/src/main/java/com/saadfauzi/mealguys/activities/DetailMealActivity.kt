package com.saadfauzi.mealguys.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.saadfauzi.mealguys.databinding.ActivityDetailMealBinding
import com.saadfauzi.mealguys.viewmodels.DetailMealViewModel

class DetailMealActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailMealBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<DetailMealViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val getIdMeal = intent.getStringExtra(EXTRA_ID_MEAL)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        if (getIdMeal != null) {
            viewModel.getMealDetails(getIdMeal)
            viewModel.mealDetails.observe(this) {}
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbMealDetails.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_ID_MEAL = "id_meal"
    }
}