package com.example.kotlintemplate.ui.screen.rakuten

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlintemplate.data.api.ApiRepositoryInterface
import com.example.kotlintemplate.data.entity.RakutenEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface RakutenUiState {
    data object Idle : RakutenUiState
    data object Loading : RakutenUiState
    data class Success(val data: RakutenEntity) : RakutenUiState
    data class Error(val message: String) : RakutenUiState
}

@HiltViewModel
class RakutenViewModel @Inject constructor(
    private val apiRepository: ApiRepositoryInterface
) : ViewModel() {
    private val _uiState = MutableStateFlow<RakutenUiState>(RakutenUiState.Idle)
    val uiState: StateFlow<RakutenUiState> = _uiState.asStateFlow()

    fun search() {
        viewModelScope.launch {
            _uiState.value = RakutenUiState.Loading

            try {
                val result = apiRepository.rakutenSearch(
                    keyword = "楽天",
                    page = 1
                )
                _uiState.value = RakutenUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value =
                    RakutenUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}