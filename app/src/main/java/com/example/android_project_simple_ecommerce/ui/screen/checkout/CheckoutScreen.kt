package com.example.android_project_simple_ecommerce.ui.screen.checkout

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.android_project_simple_ecommerce.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val cartItems by cartViewModel.cartItems.collectAsState()

    var buyerName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var courier by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    val courierOptions = listOf("JNE", "J&T", "SiCepat")
    val paymentOptions = listOf("M-Banking", "QRIS", "Transfer Bank")

    var courierExpanded by remember { mutableStateOf(false) }
    var paymentExpanded by remember { mutableStateOf(false) }

    // Upload bukti pembayaran
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Checkout") })
        },
        bottomBar = {
            BottomAppBar {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = {
                        cartViewModel.clearCart()
                        navController.navigate("success")
                    }
                ) {
                    Text("Beli Sekarang")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = buyerName,
                onValueChange = { buyerName = it },
                label = { Text("Nama Pembeli") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Alamat Pembeli") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = courierExpanded,
                onExpandedChange = { courierExpanded = !courierExpanded }
            ) {
                OutlinedTextField(
                    value = courier,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Kurir Pengantaran") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = courierExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = courierExpanded,
                    onDismissRequest = { courierExpanded = false }
                ) {
                    courierOptions.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                courier = it
                                courierExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = paymentExpanded,
                onExpandedChange = { paymentExpanded = !paymentExpanded }
            ) {
                OutlinedTextField(
                    value = paymentMethod,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Metode Pembayaran") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = paymentExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = paymentExpanded,
                    onDismissRequest = { paymentExpanded = false }
                ) {
                    paymentOptions.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                paymentMethod = it
                                paymentExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Catatan Pembelian (opsional)") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            Spacer(Modifier.height(16.dp))

            // Upload Gambar
            Text("Bukti Pembayaran")
            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = {
                    launcher.launch("image/*")
                }) {
                    Text("Pilih Gambar")
                }

                Spacer(Modifier.width(12.dp))

                imageUri?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = "Bukti Pembayaran",
                        modifier = Modifier.size(80.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Ringkasan
            Text("Ringkasan Pesanan:")
            cartItems.forEach {
                Text("- ${it.title} (${it.quantity}x)")
            }

            Spacer(Modifier.height(8.dp))

            Text("Total: Rp ${cartItems.sumOf { it.price * it.quantity }}")
        }
    }
}
