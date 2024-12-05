package com.berlin.chit_chat.auth.presentation.register

sealed interface RegisterState{
    data object Idle: RegisterState
    data object Loading: RegisterState
    data object Success: RegisterState
    data class Error(val message: String): RegisterState
}
