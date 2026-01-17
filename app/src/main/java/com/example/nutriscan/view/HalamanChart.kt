package com.example.nutriscan.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.nutriscan.ui.theme.GradientButton
import com.example.nutriscan.viewmodel.ChartViewModel
import androidx.compose.ui.graphics.Color


@Composable
fun HalamanChart(
    chartVM: ChartViewModel,
    kembaliKeHome: () -> Unit
) {
    val dataHarian = chartVM.kaloriMingguan.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Grafik Kalori Harian",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (dataHarian.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada riwayat kalori harian.")
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                AndroidView(
                    factory = { context ->
                        BarChart(context).apply {
                            val entries = dataHarian.value.mapIndexed { index, value ->
                                BarEntry(index.toFloat(), value.toFloat())
                            }

                            val dataSet = BarDataSet(entries, "Kalori (kcal)").apply {
                                // Grafik hijau
                                color = android.graphics.Color.rgb(42, 118, 46)
                                valueTextColor = android.graphics.Color.BLACK
                                valueTextSize = 12f
                            }

                            this.data = BarData(dataSet)

                            // Sumbu X
                            xAxis.apply {
                                valueFormatter = IndexAxisValueFormatter(
                                    listOf("H1", "H2", "H3", "H4", "H5", "H6", "H7")
                                )
                                position = XAxis.XAxisPosition.BOTTOM
                                setDrawGridLines(false)
                                textColor = android.graphics.Color.BLACK
                                textSize = 10f
                                granularity = 1f
                            }

                            // Sumbu Y
                            axisLeft.apply {
                                textColor = android.graphics.Color.BLACK
                                axisMinimum = 0f
                            }
                            axisRight.isEnabled = false

                            description = Description().apply { text = "" }
                            legend.textColor = android.graphics.Color.BLACK

                            animateY(1000)
                        }
                    },
                    update = { chart ->
                        val entries = dataHarian.value.mapIndexed { index, value ->
                            BarEntry(index.toFloat(), value.toFloat())
                        }
                        val dataSet = BarDataSet(entries, "Kalori (kcal)").apply {
                            color = android.graphics.Color.rgb(42, 118, 46)
                            valueTextColor = android.graphics.Color.BLACK
                        }
                        chart.data = BarData(dataSet)
                        chart.invalidate()
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        GradientButton(
            text = "Kembali",
            onClick = kembaliKeHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    }
}

