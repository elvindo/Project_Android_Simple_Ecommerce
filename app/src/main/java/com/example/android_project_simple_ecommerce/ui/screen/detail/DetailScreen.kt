package com.example.android_project_simple_ecommerce.ui.screen.detail

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.android_project_simple_ecommerce.data.model.Product
import com.example.android_project_simple_ecommerce.viewmodel.CartViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.android_project_simple_ecommerce.data.model.CartItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    product: Product,
    navController: NavController,
    cartViewModel: CartViewModel
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Detail Produk") })
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(product.title, fontWeight = FontWeight.Bold)
            Text("Rp ${product.price}")
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    cartViewModel.addToCart(
                        CartItem(
                            productId = product.id,
                            title = product.title,
                            price = product.price,
                            image = product.image
                        )
                    )
                    navController.popBackStack()
                }
            ) {
                Text("Tambah ke Keranjang")
            }
        }
    }
}

