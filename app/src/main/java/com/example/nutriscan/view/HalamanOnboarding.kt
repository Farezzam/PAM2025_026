package com.example.nutriscan.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nutriscan.R
import kotlinx.coroutines.launch

@Composable
fun HalamanOnboarding(
    onMulai: () -> Unit
) {
    val pages = listOf(
        OnboardingPage(
            title = "Pantau Kalori Harianmu",
            desc = "Catat makanan yang kamu konsumsi setiap hari secara mudah dan cepat.",
            image = R.drawable.phone // Pastikan nama file di res/drawable benar
        ),
        OnboardingPage(
            title = "Kelola Pola Makan",
            desc = "Lihat riwayat makanan dan pantau kesehatanmu dengan lebih baik.",
            image = R.drawable.checklist
        ),
        OnboardingPage(
            title = "Grafik Kalori Mingguan",
            desc = "Analisa perkembangan pola makan selama 7 hari terakhir.",
            image = R.drawable.charts
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Memberikan weight(1f) agar Pager mengambil ruang sisa
            // dan tidak mendorong tombol keluar layar
            Box(modifier = Modifier.weight(1f)) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { index ->
                    OnboardingItem(page = pages[index])
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Tombol Next / Mulai tetap di bawah
            Button(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage < pages.lastIndex) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            onMulai() // Menuju login
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp) // Memberi tinggi pasti agar jelas terlihat
            ) {
                Text(
                    text = if (pagerState.currentPage == pages.lastIndex)
                        "Mulai"
                    else
                        "Lanjut"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun OnboardingItem(page: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxWidth(), // JANGAN fillMaxSize di sini
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = page.image),
            contentDescription = page.title,
            modifier = Modifier
                .size(260.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = page.desc,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

data class OnboardingPage(
    val title: String,
    val desc: String,
    val image: Int
)