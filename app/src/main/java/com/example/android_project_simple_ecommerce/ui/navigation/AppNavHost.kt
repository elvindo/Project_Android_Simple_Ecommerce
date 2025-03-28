package com.example.android_project_simple_ecommerce.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android_project_simple_ecommerce.ui.screen.home.HomeScreen
import com.example.android_project_simple_ecommerce.viewmodel.ProductViewModel

@Composable
fun AppNavHost(navController: NavHostController, viewModel: ProductViewModel) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(viewModel, navController) }
        // detail, cart, dll nanti
    }
}
