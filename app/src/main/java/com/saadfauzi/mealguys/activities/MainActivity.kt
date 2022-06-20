package com.saadfauzi.mealguys.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.saadfauzi.mealguys.R
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
    private lateinit var auth: FirebaseAuth

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val notConnected = intent.getBooleanExtra(
                ConnectivityManager
                .EXTRA_NO_CONNECTIVITY, false)
            if (notConnected) {
                disconnected()
            } else {
                connected()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        binding.rvCountry.setHasFixedSize(true)

//        initViewModels()

        binding.icSettings.setOnClickListener {
            val popUpMenu = PopupMenu(this@MainActivity, binding.icSettings)
            popUpMenu.menuInflater.inflate(R.menu.main_menu, popUpMenu.menu)
            popUpMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.settings_menu -> startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                    R.id.about_menu -> {
                        Toast.makeText(this@MainActivity, "About", Toast.LENGTH_SHORT).show()
                    }
                    R.id.logout_menu -> alertLogout()
                }
                true
            }
            popUpMenu.show()
        }

        binding.icAvatar.setOnClickListener {
            val popUpMenu = PopupMenu(this@MainActivity, binding.icAvatar)
            popUpMenu.menuInflater.inflate(R.menu.user_menu, popUpMenu.menu)
            popUpMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.bookmark_menu -> startActivity(Intent(this@MainActivity, BookmarkActivity::class.java))
                    R.id.profile_menu -> startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
                }
                true
            }
            popUpMenu.show()
        }

    }

    @Suppress("DEPRECATION")
    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    override fun onBackPressed() {
        alertCloseApp()
    }

    private fun initViewModels() {
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

    private fun alertLogout() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle(R.string.app_name)
            setMessage(R.string.message_logout)
            setPositiveButton(
                R.string.yes
            ) { _, _ ->
                signOut()
            }
            setNegativeButton(
                R.string.no
            ) { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.create()
        builder.show()
    }

    private fun alertCloseApp() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle(R.string.app_name)
            setMessage(R.string.message_close_app)
            setPositiveButton(
                R.string.yes
            ) { _, _ ->
                finish()
            }
            setNegativeButton(
                R.string.no
            ) { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.create()
        builder.show()
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbMain.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun disconnected() {
        binding.rvCountry.visibility = View.INVISIBLE
        binding.rvMealsCategory.visibility = View.INVISIBLE
        binding.imgNoConnection.visibility = View.VISIBLE
    }

    private fun connected() {
        binding.rvCountry.visibility = View.VISIBLE
        binding.rvMealsCategory.visibility = View.VISIBLE
        binding.imgNoConnection.visibility = View.GONE
        initViewModels()
    }
}