package com.saadfauzi.mealguys.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.saadfauzi.mealguys.R
import com.saadfauzi.mealguys.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val auth = Firebase.auth

        val firebaseUser = auth.currentUser
        val emailUser = "Email: ${firebaseUser?.email}"
        val displayName = firebaseUser?.displayName
        val uId = "Your id: ${firebaseUser?.uid}"
        val photoUrl = firebaseUser?.photoUrl
        val phone = "Phone number: ${firebaseUser?.phoneNumber}"

        binding.apply {
            if (displayName != null) {
                tvDisplayName.text = displayName
            } else {
                tvDisplayName.text = firebaseUser?.email
            }
            if (photoUrl != null) {
                Glide.with(this@ProfileActivity)
                    .load(photoUrl)
                    .placeholder(R.drawable.ic_placeholder_avatar)
                    .into(photoProfile)
            } else {
                photoProfile.setImageResource(R.drawable.ic_placeholder_avatar)
            }
            phoneNumber.text = phone
            email.text = emailUser
            idUser.text = uId
            btnLogout.setOnClickListener { signOut() }
        }
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}