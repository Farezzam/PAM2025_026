package com.example.nutriscan.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nutriscan.ui.theme.GradientButton
import com.example.nutriscan.viewmodel.FoodViewModel
import java.text.SimpleDateFormat
import java.util.*

fun formatTanggal(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@Composable
fun HalamanHistory(
    foodVM: FoodViewModel,
    kembaliKeHome: () -> Unit,
    keEdit: (Int) -> Unit
) {

    val daftar = foodVM.riwayat.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Riwayat Makanan",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Jika tidak ada data
        if (daftar.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada riwayat makanan.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f) // supaya tombol tetap di bawah
            ) {
                items(daftar.value) { item ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { keEdit(item.id) }, // Error hilang setelah parameter ditambah
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize() // pastikan Box menutupi seluruh Card
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(Color(0xFF2A762E), Color(0xFF08AF11))
                                    )
                                )
                                .padding(16.dp)
                        ) {
                            Column {
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Kalori: ${item.calories} kcal",
                                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Tanggal: ${formatTanggal(item.timestamp)}",
                                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol Kembali di bawah
        GradientButton(
            text = "Kembali",
            onClick = kembaliKeHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    }
}
