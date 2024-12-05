package com.berlin.chit_chat.auth.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.berlin.chit_chat.auth.presentation.login.LoginState
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
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state = _state.asStateFlow()

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        if (auth.currentUser == null) {
            _state.value = LoginState.Idle
        } else {
            _state.value = LoginState.Success
        }
    }

    fun login(email: String, password: String) {
        _state.value = LoginState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _state.value = LoginState.Success
                } else {
                    val exception = task.exception
                    when (exception) {
                        is FirebaseAuthInvalidUserException -> {
                            _state.value = LoginState.Error("No account found with this email.")
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            _state.value = LoginState.Error("Incorrect password. Please try again.")
                        }
                        else -> {
                            _state.value = LoginState.Error(exception?.message ?: "Login failed")
                        }
                    }

                    Log.e("FirebaseAuth", "Login failed", exception)
                }
            }
    }


}