package com.example.nutriscan.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nutriscan.ui.theme.GradientButton
import com.example.nutriscan.viewmodel.FoodViewModel

@Composable
fun HalamanEdit(
    foodId: Int,
    foodVM: FoodViewModel,
    kembali: () -> Unit
) {
    val riwayat by foodVM.riwayat.collectAsState()
    val item = riwayat.find { it.id == foodId }

    var nama by remember { mutableStateOf(item?.name ?: "") }
    var kalori by remember { mutableStateOf(item?.calories?.toString() ?: "") }

    Column (
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Edit Data Makanan", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("Nama Makanan") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = kalori,
            onValueChange = { kalori = it },
            label = { Text("Kalori (kcal)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tombol Simpan (Update)
        GradientButton(
            text = "Simpan Perubahan",
            onClick = {
                item?.let {
                    foodVM.updateMakanan(it.id, nama, kalori.toIntOrNull() ?: 0, it.portion, it.timestamp)
                    kembali()
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Tombol Hapus
        Button(
            onClick = {
                item?.let {
                    foodVM.hapusMakanan(it.id, it.name, it.calories, it.portion, it.timestamp)
                    kembali()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD32F2F) // merah solid
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Hapus",
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Hapus Data",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}