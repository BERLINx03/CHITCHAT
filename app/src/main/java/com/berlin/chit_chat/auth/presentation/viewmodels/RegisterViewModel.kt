package com.berlin.chit_chat.auth.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.berlin.chit_chat.auth.presentation.login.LoginState
import com.berlin.chit_chat.auth.presentation.register.RegisterState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * @author Abdallah Elsokkary
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val state = _state.asStateFlow()

    val auth by lazy {
        FirebaseAuth.getInstance()
    }


    fun register(userName: String,email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            _state.value = RegisterState.Error("Passwords do not match")
            return
        }
        _state.value = RegisterState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.let { user ->
                        user.updateProfile(
                            com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                .setDisplayName(userName)
                                .build()
                        ).addOnCompleteListener {
                            _state.value = RegisterState.Success
                        }
                        return@addOnCompleteListener
                    }
                    _state.value = RegisterState.Error(task.exception?.message ?: "Unknown error")
                } else {
                    _state.value = RegisterState.Error(task.exception?.message ?: "Unknown error")
                }
            }
    }
}