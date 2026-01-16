package com.example.nutriscan.view

import androidx.compose.foundation.Image
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
            image = R.drawable.onboard1
        ),
        OnboardingPage(
            title = "Kelola Pola Makan",
            desc = "Lihat riwayat makanan dan pantau kesehatanmu dengan lebih baik.",
            image = R.drawable.onboard2
        ),
        OnboardingPage(
            title = "Grafik Kalori Mingguan",
            desc = "Analisa perkembangan pola makan selama 7 hari terakhir.",
            image = R.drawable.onboard3
        )
    )

    val pagerState = rememberPagerState (pageCount = { pages.size })
    val scope = rememberCoroutineScope ()

    Surface (modifier = Modifier.fillMaxSize()) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            HorizontalPager (state = pagerState) { index ->
                OnboardingItem(page = pages[index])
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Tombol Next / Mulai
            Button (
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage < pages.lastIndex) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            onMulai() // Menuju login
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (pagerState.currentPage == pages.lastIndex)
                        "Mulai"
                    else
                        "Lanjut"
                )
            }
        }
    }
}

@Composable
fun OnboardingItem(page: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = page.image),
            contentDescription = page.title,
            modifier = Modifier
                .size(260.dp)
                .padding(top = 30.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = page.desc,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

data class OnboardingPage(
    val title: String,
    val desc: String,
    val image: Int
)