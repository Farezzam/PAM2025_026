package com.example.nutriscan.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.Description
import android.graphics.Color
import com.example.nutriscan.viewmodel.ChartViewModel

@Composable
fun HalamanChart(
    chartVM: ChartViewModel,
    kembaliKeHome: () -> Unit
) {

    val dataMingguan = chartVM.kaloriMingguan.collectAsState(initial = emptyList())
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
                    text = "Grafik Kalori Mingguan",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f)
                )

                Button (onClick = kembaliKeHome) {
                    Text("Home")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Jika tidak ada data
            if (dataMingguan.value.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Belum ada data untuk ditampilkan.")
                }
            } else {
                GrafikKaloriMingguan(dataMingguan.value)
            }
        }
    }
}

@Composable
fun GrafikKaloriMingguan(data: List<Int>) {

    val context = LocalContext.current

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
        factory = {
            BarChart(context).apply {

                val entries = data.mapIndexed { index, value ->
                    BarEntry(index.toFloat(), value.toFloat())
                }

                val dataSet = BarDataSet(entries, "Kalori (kcal)").apply {
                    color = Color.rgb(100, 181, 246) // biru muda
                    valueTextColor = Color.BLACK
                    valueTextSize = 12f
                }

                val barData = BarData(dataSet)
                this.data = barData

                // Format sumbu X (Hari 1-7)
                xAxis.valueFormatter = IndexAxisValueFormatter(
                    listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min")
                )
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.textColor = Color.BLACK
                xAxis.textSize = 12f
                axisLeft.textColor = Color.BLACK
                axisRight.isEnabled = false

                description = Description().apply { text = "" } // hilangkan watermark "Description"

                legend.textColor = Color.BLACK
                animateY(900)
            }
        },
        update = { chart ->

            val entries = data.mapIndexed { index, value ->
                BarEntry(index.toFloat(), value.toFloat())
            }

            val dataSet = BarDataSet(entries, "Kalori (kcal)").apply {
                color = Color.rgb(100, 181, 246)
                valueTextColor = Color.BLACK
            }

            chart.data = BarData(dataSet)
            chart.invalidate()
        }
    )
}