package com.example.nutriscan.view

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
    kembaliKeHome: () -> Unit
) {

    val daftar = foodVM.riwayat.collectAsState()


    Surface (
        modifier = Modifier.fillMaxSize()
    ) {

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Riwayat Makanan",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f)
                )

                Button (onClick = kembaliKeHome) {
                    Text("Home")
                }
            }

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
                LazyColumn (
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(daftar.value) { item ->
                        Card (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {

                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "Kalori: ${item.calories} kcal",
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "Tanggal: ${formatTanggal(item.timestamp)}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}