package com.berlin.chit_chat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.berlin.chit_chat.auth.presentation.login.LoginScreen
import com.berlin.chit_chat.auth.presentation.register.RegisterScreen
import com.berlin.chit_chat.chat.presentation.HomeScreen
import com.berlin.chit_chat.util.Screens

/**
 * @author Abdallah Elsokkary
 */
@Composable
fun ChitChatApp(modifier: Modifier = Modifier) {

    val navController: NavHostController = rememberNavController()

    Surface(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = Screens.LoginScreen.route
        ){
            composable(Screens.LoginScreen.route){
                LoginScreen(navController = navController)
            }
            composable(Screens.RegisterScreen.route){
                RegisterScreen(navController = navController)
            }
            composable(Screens.HomeScreen.route){
                HomeScreen(navController = navController)
            }

        }
    }
}