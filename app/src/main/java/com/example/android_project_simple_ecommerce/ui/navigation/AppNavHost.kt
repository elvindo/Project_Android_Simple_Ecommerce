package com.example.android_project_simple_ecommerce.ui.navigation

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android_project_simple_ecommerce.ui.screen.cart.CartScreen
import com.example.android_project_simple_ecommerce.ui.screen.checkout.CheckoutScreen
import com.example.android_project_simple_ecommerce.ui.screen.detail.DetailScreen
import com.example.android_project_simple_ecommerce.ui.screen.success.SuccessScreen
import com.example.android_project_simple_ecommerce.viewmodel.CartViewModel
import com.example.android_project_simple_ecommerce.viewmodel.ProductViewModel

@Composable
fun AppNavHost(navController: NavHostController, viewModel: ProductViewModel) {
    val cartViewModel: CartViewModel = hiltViewModel()

    NavHost(navController, startDestination = "home") {

        composable("home") {
            HomeScreen(viewModel, navController)
        }

        composable("detail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            val product = viewModel.productList.find { it.id == productId }
            product?.let {
                DetailScreen(
                    product = it,
                    navController = navController,
                    cartViewModel = cartViewModel
                )
            }
        }

        composable("cart") {
            CartScreen(navController = navController, cartViewModel = cartViewModel)
        }

        composable("checkout") {
            CheckoutScreen(navController, cartViewModel)
        }

        composable("success") {
            SuccessScreen(navController)
        }
    }
}

