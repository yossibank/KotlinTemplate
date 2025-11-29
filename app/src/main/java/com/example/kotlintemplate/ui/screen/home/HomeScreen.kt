package com.example.kotlintemplate.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.multiplatform.Sample

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onClickDetail: () -> Unit,
    onClickRakuten: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("ホーム画面")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ホーム",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = Sample().string(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Button(onClick = onClickDetail) {
                Text("詳細画面遷移")
            }
            Button(onClick = onClickRakuten) {
                Text("楽天画面遷移")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onClickDetail = {},
        onClickRakuten = {}
    )
}