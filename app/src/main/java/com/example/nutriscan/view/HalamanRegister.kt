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
fun HalamanRegister(
    authVM: AuthViewModel,
    keLogin: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var konfirmasiPassword by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }
    var suksesDaftar by remember { mutableStateOf(false) }

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

        // Form register
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
                    text = "Daftar Akun",
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

                Spacer(modifier = Modifier.height(12.dp))

                // Konfirmasi Password
                OutlinedTextField(
                    value = konfirmasiPassword,
                    onValueChange = { konfirmasiPassword = it },
                    label = { Text("Konfirmasi Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Tombol gradient Daftar
                GradientButton(
                    text = "Daftar",
                    onClick = {
                        if (email.isBlank() || password.isBlank() || konfirmasiPassword.isBlank()) {
                            errorMessage = "Semua field harus diisi"
                            suksesDaftar = false
                        } else if (password != konfirmasiPassword) {
                            errorMessage = "Password tidak sama"
                            suksesDaftar = false
                        } else {
                            authVM.register(email.trim(), password.trim())
                            suksesDaftar = true
                            errorMessage = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = keLogin) {
                    Text("Sudah punya akun? Login di sini")
                }

                // Pesan error
                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }

                // Pesan sukses
                if (suksesDaftar) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Pendaftaran berhasil! Silakan login.",
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}