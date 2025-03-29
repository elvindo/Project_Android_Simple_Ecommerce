package com.example.android_project_simple_ecommerce.ui.screen.checkout

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.android_project_simple_ecommerce.viewmodel.CartViewModel
import kotlinx.coroutines.launch

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
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val courierOptions = listOf("JNE", "J&T", "SiCepat")
    val paymentOptions = listOf("M-Banking", "QRIS", "Transfer Bank")

    var courierExpanded by remember { mutableStateOf(false) }
    var paymentExpanded by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Checkout", modifier = Modifier.padding(end = 42.dp))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomAppBar(
                tonalElevation = 2.dp,
                modifier = Modifier.height(72.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total: $ ${"%.2f".format(cartItems.sumOf { it.price * it.quantity })}")
                    Button(
                        onClick = {
                            if (
                                buyerName.isBlank() || address.isBlank() ||
                                courier.isBlank() || paymentMethod.isBlank() || imageUri == null
                            ) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Order data is incomplete")
                                }
                            } else {
                                cartViewModel.clearCart()
                                navController.navigate("success")
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .height(48.dp)
                            .width(150.dp)
                    ) {
                        Text("Buy")
                    }
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
            Text(
                "Order Data",
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
            Spacer(Modifier.height(32.dp))

            listOf(
                "Name" to buyerName,
                "Recipient Address" to address
            ).forEachIndexed { index, pair ->
                Text(text = pair.first, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 4.dp))
                TextField(
                    value = if (index == 0) buyerName else address,
                    onValueChange = {
                        if (index == 0) buyerName = it else address = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Gray
                    )
                )
                Spacer(Modifier.height(24.dp))
            }

            Text("Delivery Courier", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 4.dp))
            ExposedDropdownMenuBox(
                expanded = courierExpanded,
                onExpandedChange = { courierExpanded = !courierExpanded }
            ) {
                TextField(
                    value = courier,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select courier") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(courierExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Gray
                    )
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

            Spacer(Modifier.height(24.dp))
            Text("Payment Method", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 4.dp))
            ExposedDropdownMenuBox(
                expanded = paymentExpanded,
                onExpandedChange = { paymentExpanded = !paymentExpanded }
            ) {
                TextField(
                    value = paymentMethod,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select a payment method") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(paymentExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Gray
                    )
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

            Spacer(Modifier.height(24.dp))
            Text("Notes (optional)", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 4.dp))
            TextField(
                value = note,
                onValueChange = { note = it },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Spacer(Modifier.height(24.dp))
            Text("Proof of payment", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("Tap here to select image")
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("Order Summary:", fontWeight = FontWeight.Bold)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                    .padding(24.dp)
            ) {
                cartItems.forEach {
                    Text("- ${it.title} (${it.quantity}x)", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
