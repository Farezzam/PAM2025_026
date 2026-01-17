package com.example.nutriscan.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nutriscan.R
import com.example.nutriscan.ui.theme.GradientButton
import com.example.nutriscan.viewmodel.AuthViewModel

@Composable
fun HalamanLogin(
    authVM: AuthViewModel,
    onLoginBerhasil: () -> Unit,
    keRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginResult by authVM.loginResult.collectAsState()

    // Jika login berhasil → navigasi
    LaunchedEffect(loginResult) {
        if (loginResult == true) {
            onLoginBerhasil()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background full-screen dengan crop
        Image(
            painter = painterResource(id = R.drawable.loadingscreen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            alpha = 0.7f // gelapkan background
        )

        // Overlay hitam transparan
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x55000000))
        )

        // Form login
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .background(
                    Color.White.copy(alpha = 0.95f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(24.dp)
                .align(Alignment.Center)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Login NutriScan",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Input Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Input Password
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Tombol gradient login
                GradientButton(
                    text = "Masuk",
                    onClick = { authVM.login(email.trim(), password.trim()) },
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Link ke register
                TextButton(onClick = keRegister) {
                    Text("Belum punya akun? Daftar di sini")
                }

                // Pesan error
                if (loginResult == false) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Email atau password salah",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}