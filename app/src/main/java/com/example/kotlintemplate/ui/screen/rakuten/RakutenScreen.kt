package com.example.kotlintemplate.ui.screen.rakuten

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kotlintemplate.data.api.ApiRepositoryInterface
import com.example.kotlintemplate.data.api.FakeApiRepository
import yossibank.shared.foundation.value.ValueFormatter
import yossibank.shared.foundation.value.ValueStyle
import yossibank.shared.foundation.value.ValueSuffix

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RakutenScreen(
    viewModel: RakutenViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("詳細画面")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
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
                text = "楽天画面",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = {
                    viewModel.search()
                }
            ) {
                Text(
                    text = "検索",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            when (val uiState = uiState) {
                is RakutenUiState.Idle -> {
                    Text(
                        text = "検索してください",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                is RakutenUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is RakutenUiState.Success -> {
                    val data = uiState.data
                    LazyColumn {
                        items(data.items) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = item.itemName,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = ValueFormatter(value = item.itemPrice.toDouble()).format(
                                            style = ValueStyle(suffix = ValueSuffix.Yen)
                                        ),
                                        color = Color.Red,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
                is RakutenUiState.Error -> {
                    val message = uiState.message
                    Text(
                        text = "エラー: $message",
                        color = Color.Red,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun previewRakutenViewModel(
    apiRepository: ApiRepositoryInterface = FakeApiRepository()
): RakutenViewModel {
    return remember {
        RakutenViewModel(apiRepository = apiRepository)
    }
}


@Preview(showBackground = true)
@Composable
fun RakutenScreenPreview() {
    RakutenScreen(
        viewModel = previewRakutenViewModel(),
        onBackClick = {}
    )
}

