package com.example.nutriscan.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nutriscan.ui.theme.GradientButton
import com.example.nutriscan.viewmodel.FoodViewModel

@Composable
fun HalamanInput(
    foodVM: FoodViewModel,
    kembaliKeHome: () -> Unit
) {
    var namaMakanan by remember { mutableStateOf("") }
    var kalori by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var suksesTambah by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Tambah Makanan",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = namaMakanan,
            onValueChange = { namaMakanan = it },
            label = { Text("Nama Makanan") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = kalori,
            onValueChange = { kalori = it },
            label = { Text("Kalori (kcal)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(20.dp))

        GradientButton(
            text = "Simpan",
            onClick = {
                if (namaMakanan.isBlank() || kalori.isBlank()) {
                    errorMessage = "Semua field harus diisi"
                    suksesTambah = false
                    return@GradientButton
                }

                val nilaiKalori = kalori.toIntOrNull()
                if (nilaiKalori == null || nilaiKalori <= 0) {
                    errorMessage = "Kalori harus berupa angka dan lebih dari 0"
                    suksesTambah = false
                    return@GradientButton
                }

                // Panggil ViewModel
                foodVM.tambahMakanan(
                    nama = namaMakanan.trim(),
                    kalori = nilaiKalori
                )

                suksesTambah = true
                errorMessage = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }

        if (suksesTambah) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Berhasil ditambahkan!",
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        GradientButton(
            text = "Kembali",
            onClick = kembaliKeHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
    }
}
