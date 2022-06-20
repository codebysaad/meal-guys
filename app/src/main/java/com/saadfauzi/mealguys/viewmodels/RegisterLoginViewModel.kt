package com.saadfauzi.mealguys.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.saadfauzi.mealguys.helpers.Event

class RegisterLoginViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isMessage = MutableLiveData<Event<String?>>()
    val isMessage: LiveData<Event<String?>> = _isMessage

    private val _response = MutableLiveData<FirebaseUser?>()
    val response: LiveData<FirebaseUser?> = _response

    fun loginWithEmailPassword(email: String, password: String) {
        _isLoading.value = true
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    Log.d("AuthUsingEmailPassword", "signInWithEmail:success")
                    _response.value = auth.currentUser
                } else {
                    Log.e("AuthUsingEmailPassword", "signInWithEmail:failure", task.exception)
                    _response.value = null
                }
            }
            .addOnFailureListener { e ->
                _isLoading.value = false
                _isMessage.value = Event(e.message.toString())
                Log.e("AuthUsingEmailPassword", "signInWithEmail:failure", e)
            }
    }

    fun createUserWithEmailPassword(email: String, password: String) {
        _isLoading.value = true
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                _isLoading.value = false
                if (it.isSuccessful) {
                    _response.value = auth.currentUser
                } else {
                    _response.value = null
                }
            }
            .addOnFailureListener { e ->
                _isLoading.value = false
                _isMessage.value = Event(e.message.toString())
                _response.value = null
            }
    }
}