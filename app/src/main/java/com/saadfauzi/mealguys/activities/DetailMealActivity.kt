package com.saadfauzi.mealguys.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.saadfauzi.mealguys.R
import com.saadfauzi.mealguys.database.MealsDao
import com.saadfauzi.mealguys.database.MealsEntity
import com.saadfauzi.mealguys.databinding.ActivityDetailMealBinding
import com.saadfauzi.mealguys.viewmodels.DetailMealViewModel
import com.saadfauzi.mealguys.viewmodels.DetailViewModelFactory


class DetailMealActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailMealBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: DetailMealViewModel
    private lateinit var getIdMeal: String
    private lateinit var mealsEntity: MealsEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getIdMeal = intent.getStringExtra(EXTRA_ID_MEAL).toString()

        viewModel = obtainViewModel()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.getItemIsBookmarked(getIdMeal)

        viewModel.getMealDetails(getIdMeal)
        viewModel.mealDetails.observe(this) { detail ->
            mealsEntity = MealsEntity(
                detail.meals[0].idMeal,
                detail.meals[0].strMealThumb,
                detail.meals[0].strMeal,
                true
            )
            Glide.with(this)
                .load(detail.meals[0].strMealThumb)
                .placeholder(R.drawable.ic_place_holder)
                .into(binding.icThumbnail)
            binding.tvMealName.text = detail.meals[0].strMeal
            binding.tvArea.text = detail.meals[0].strArea
            binding.tvCategory.text = detail.meals[0].strCategory
            val tags = "#${detail.meals[0].strTags}"
            binding.tvTags.text = tags
            val ingredients = "${detail.meals[0].strIngredient1}: ${detail.meals[0].strMeasure1}\n" +
                    "${detail.meals[0].strIngredient2}: ${detail.meals[0].strMeasure2}\n" +
                    "${detail.meals[0].strIngredient3}: ${detail.meals[0].strMeasure3}\n" +
                    "${detail.meals[0].strIngredient4}: ${detail.meals[0].strMeasure4}\n" +
                    "${detail.meals[0].strIngredient5}: ${detail.meals[0].strMeasure5}\n" +
                    "${detail.meals[0].strIngredient6}: ${detail.meals[0].strMeasure6}\n" +
                    "${detail.meals[0].strIngredient7}: ${detail.meals[0].strMeasure7}\n" +
                    "${detail.meals[0].strIngredient8}: ${detail.meals[0].strMeasure8}\n" +
                    "${detail.meals[0].strIngredient9}: ${detail.meals[0].strMeasure9}\n" +
                    "${detail.meals[0].strIngredient10}: ${detail.meals[0].strMeasure10}\n" +
                    "${detail.meals[0].strIngredient11}: ${detail.meals[0].strMeasure11}\n" +
                    "${detail.meals[0].strIngredient12}: ${detail.meals[0].strMeasure12}\n" +
                    "${detail.meals[0].strIngredient13}: ${detail.meals[0].strMeasure13}\n" +
                    "${detail.meals[0].strIngredient14}: ${detail.meals[0].strMeasure14}\n" +
                    "${detail.meals[0].strIngredient15}: ${detail.meals[0].strMeasure15}\n" +
                    "${detail.meals[0].strIngredient16}: ${detail.meals[0].strMeasure16}\n" +
                    "${detail.meals[0].strIngredient17}: ${detail.meals[0].strMeasure17}\n" +
                    "${detail.meals[0].strIngredient18}: ${detail.meals[0].strMeasure18}\n" +
                    "${detail.meals[0].strIngredient19}: ${detail.meals[0].strMeasure19}\n" +
                    "${detail.meals[0].strIngredient20}: ${detail.meals[0].strMeasure20}\n"
            binding.tvIngredient.text = ingredients
            binding.tvInstruction.text = detail.meals[0].strInstructions
            binding.btnViewYt.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(detail.meals[0].strYoutube))
                startActivity(intent)
            }
        }
    }

    private fun obtainViewModel(): DetailMealViewModel {
        val factory = DetailViewModelFactory.getInstance(application)
        return ViewModelProvider(this, factory)[DetailMealViewModel::class.java]
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item = menu!!.findItem(R.id.bookmark_menu)
        viewModel.getItemIsBookmarked(getIdMeal).observe(this) {
            when(it) {
                true -> {
                    item.title = resources.getString(R.string.unbookmark)
                }
                false -> {
                    item.title = resources.getString(R.string.bookmark)
                }
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.bookmark_menu -> {
                val titleBookmark = resources.getString(R.string.bookmark)
                if (item.title == titleBookmark) {
                    viewModel.addBookmarkMeal(mealsEntity)
                    showToast(getString(R.string.success_add_to_bookmark))
                } else {
                    viewModel.deleteBookmarkedMeal(getIdMeal)
                    showToast(getString(R.string.success_delete_bookmark_meal))
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbMealDetails.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this@DetailMealActivity, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_ID_MEAL = "id_meal"
    }
}