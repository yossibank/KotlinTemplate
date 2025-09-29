package com.example.kotlintemplate.ui.screen.rakuten

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlintemplate.data.api.ApiRepositoryInterface
import kotlinx.coroutines.launch

class RakutenViewModel(
    apiRepository: ApiRepositoryInterface
) : ViewModel() {
    private val mApiRepository = apiRepository

    fun search() {
        viewModelScope.launch {
            val result = mApiRepository.rakutenSearch(
                keyword = "楽天",
                page = 1
            )
        }
    }
}