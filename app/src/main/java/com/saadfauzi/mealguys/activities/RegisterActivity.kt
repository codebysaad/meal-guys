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
import com.google.firebase.auth.FirebaseUser
import com.saadfauzi.mealguys.R
import com.saadfauzi.mealguys.databinding.ActivityRegisterBinding
import com.saadfauzi.mealguys.viewmodels.RegisterLoginViewModel
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<RegisterLoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setButtonRegisterEnabled()
        initViewModels()
        initEditText()
        moveToLogin()

        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtConfirmPassword.text.toString()
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
                password != confirmPassword -> {
                    binding.edtConfirmPassword.error =
                        resources.getString(R.string.error_passwords_not_identical)
                }
                else -> {
                    viewModel.createUserWithEmailPassword(email, password)
                }
            }
        }
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

    private fun setButtonRegisterEnabled() {
        val emailRes = binding.edtEmail.text
        val passRes = binding.edtPassword.text
        val confirmPass = binding.edtConfirmPassword.text

        binding.btnRegister.isEnabled =
            emailRes != null && passRes != null && emailRes.contains("@") && passRes.length >= 6 && passRes != confirmPass
    }

    private fun initEditText() {
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.contains("@") == false) {
                    binding.edtEmail.error = resources.getString(R.string.error_email)
                }
                setButtonRegisterEnabled()
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
                setButtonRegisterEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.edtConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val pass = binding.edtPassword.text.toString()
                if (p0?.length != null) {
                    if (p0.length < 6) {
                        binding.edtConfirmPassword.error = resources.getString(R.string.error_pass)
                    } else if (p0.toString() != pass) {
                        binding.edtConfirmPassword.error =
                            resources.getString(R.string.error_passwords_not_identical)
                    }
                }
                setButtonRegisterEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun moveToLogin() {
        val spannableString = SpannableString(resources.getString(R.string.already_have_an_account))
        val register: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@RegisterActivity,
                        androidx.core.util.Pair(binding.tvRegister, "tv_login"),
                        androidx.core.util.Pair(binding.borderEmail, "email"),
                        androidx.core.util.Pair(binding.borderPassword, "password"),
                        androidx.core.util.Pair(binding.btnRegister, "btn_login"),
                    )
                startActivity(intent, optionsCompat.toBundle())
                finish()
            }
        }
        Log.d("Language", Locale.getDefault().language)
        if (Locale.getDefault().language == "in") {
            spannableString.setSpan(register, 18, 40, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvAlreadyAccount.apply {
                text = spannableString
                movementMethod = LinkMovementMethod.getInstance()
            }
        } else if (Locale.getDefault().language == "jv") {
            spannableString.setSpan(register, 15, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvAlreadyAccount.apply {
                text = spannableString
                movementMethod = LinkMovementMethod.getInstance()
            }
        } else {
            spannableString.setSpan(register, 25, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvAlreadyAccount.apply {
                text = spannableString
                movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbRegister.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnRegister.isEnabled = !isLoading
    }

    private fun showToast(message: String?) {
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
    }
}