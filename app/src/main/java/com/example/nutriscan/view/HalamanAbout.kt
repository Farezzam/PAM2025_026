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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nutriscan.R
import com.example.nutriscan.ui.theme.GradientButton

@Composable
fun HalamanAbout(
    kembaliKeHome: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Tentang Aplikasi",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Logo NutriScan",
                modifier = Modifier
                    .size(120.dp)
                    .padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card putih dengan sudut membulat
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        text = "Apa itu NutriScan?",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "NutriScan adalah aplikasi pemantau asupan kalori harian yang membantu pengguna " +
                                "melacak konsumsi makanan, menghitung kalori, dan menampilkan grafik tren mingguan secara otomatis.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Fitur Utama:",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Column(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("• Input makanan dan jumlah kalorinya.")
                        Text("• Melihat riwayat konsumsi makanan.")
                        Text("• Grafik tren kalori mingguan.")
                        Text("• Login dan register (lokal).")
                        Text("• Tampilan UI modern dan mudah digunakan.")
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Tombol Kembali hijau gradasi
            GradientButton(
                text = "Kembali",
                onClick = kembaliKeHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
    }
}
