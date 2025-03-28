package com.example.android_project_simple_ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.android_project_simple_ecommerce.ui.navigation.AppNavHost
import com.example.android_project_simple_ecommerce.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: ProductViewModel = hiltViewModel()
            AppNavHost(navController, viewModel)
        }
    }
}