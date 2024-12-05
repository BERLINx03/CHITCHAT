package com.berlin.chit_chat.auth.presentation.login

/**
 * @author Abdallah Elsokkary
 */
sealed interface LoginState{
    data object Idle: LoginState
    data object Loading: LoginState
    data object Success: LoginState
    data class Error(val message: String): LoginState
}
