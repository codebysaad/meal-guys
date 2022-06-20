package com.saadfauzi.mealguys.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.saadfauzi.mealguys.R
import com.saadfauzi.mealguys.databinding.ActivityLoginBinding
import com.saadfauzi.mealguys.viewmodels.RegisterLoginViewModel
import java.util.*

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private val viewModel by viewModels<RegisterLoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        setButtonLoginEnabled()
        initEditText()
        moveToRegister()

        initViewModels()

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            when {
                email.isEmpty() -> {
                    binding.edtEmail.error = resources.getString(R.string.error_empty_email)
                }
                password.isEmpty() -> {
                    binding.edtPassword.error = resources.getString(R.string.error_empty_password)
                }
                !email.contains("@") -> {
                    binding.edtEmail.error = resources.getString(R.string.error_email)
                }
                password.length < 6 -> {
                    binding.edtPassword.error = resources.getString(R.string.error_pass)
                }
                else -> {
                    viewModel.loginWithEmailPassword(email, password)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun initViewModels() {
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        viewModel.isMessage.observe(this) {
            showToast(it.getContentIfNotHandled().toString())
        }
        viewModel.response.observe(this) {
            updateUI(it)
        }
    }

    private fun setButtonLoginEnabled() {
        val emailRes = binding.edtEmail.text
        val passRes = binding.edtPassword.text

        binding.btnLogin.isEnabled =
            emailRes != null && passRes != null && emailRes.contains("@") && passRes.length >= 6
    }

    private fun showToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !isLoading
    }

    private fun initEditText() {
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.contains("@") == false) {
                    binding.edtEmail.error = resources.getString(R.string.error_email)
                }
                setButtonLoginEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length != null) {
                    if (p0.length < 6) {
                        binding.edtPassword.error = resources.getString(R.string.error_pass)
                    }
                }
                setButtonLoginEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun moveToRegister() {
        val spannableString = SpannableString(resources.getString(R.string.don_t_have_an_account))
        val register: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@LoginActivity,
                        androidx.core.util.Pair(binding.tvLogin, "tv_login"),
                        androidx.core.util.Pair(binding.borderEmail, "email"),
                        androidx.core.util.Pair(binding.borderPassword, "password"),
                        androidx.core.util.Pair(binding.btnLogin, "btn_login"),
                    )
                startActivity(intent, optionsCompat.toBundle())
                finish()
            }
        }
        Log.d("Language", Locale.getDefault().language)
        if (Locale.getDefault().language == "in") {
            spannableString.setSpan(register, 18, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvNoAccount.apply {
                text = spannableString
                movementMethod = LinkMovementMethod.getInstance()
            }
        } else if (Locale.getDefault().language == "jv") {
            spannableString.setSpan(register, 18, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvNoAccount.apply {
                text = spannableString
                movementMethod = LinkMovementMethod.getInstance()
            }
        } else {
            spannableString.setSpan(register, 23, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvNoAccount.apply {
                text = spannableString
                movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }
}