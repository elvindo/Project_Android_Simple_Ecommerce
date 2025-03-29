package com.example.android_project_simple_ecommerce.ui.screen.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.android_project_simple_ecommerce.viewmodel.CartViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val cartItems by cartViewModel.cartItems.collectAsState()

    val cartIsEmpty = cartItems.isEmpty()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Keranjang") }) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            BottomAppBar {
                Text(
                    "Total: $ ${cartItems.sumOf { it.price * it.quantity }}",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                )

                Button(
                    onClick = {
                        if (cartIsEmpty) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Keranjang masih kosong")
                            }
                        } else {
                            navController.navigate("checkout")
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Checkout")
                }
            }
        }
    ) { padding ->
        if (cartIsEmpty) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Keranjang kosong")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(cartItems) { item ->
                    Card(modifier = Modifier.padding(8.dp)) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(item.title)
                                Text("$ ${item.price}")
                            }
                            IconButton(onClick = {
                                cartViewModel.removeFromCart(item)
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Hapus")
                            }
                        }
                    }
                }
            }
        }
    }
}
