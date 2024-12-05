package com.berlin.chit_chat.util

/**
 * @author Abdallah Elsokkary
 */
sealed class Screens(val route: String){
    data object LoginScreen: Screens("login")
    data object RegisterScreen: Screens("register")
    data object HomeScreen: Screens("home")
}