package com.dvidal.stockmarketapp.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvidal.stockmarketapp.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyInfoState())
        private set

    init {
        getCompanyInfo()
    }

    private fun getCompanyInfo() = viewModelScope.launch {
        val symbol = savedStateHandle.get<String>("companySymbol") ?: return@launch
        state = state.copy(isLoading = true)

        val companyInfoResult = async { repository.getCompanyInfo(symbol) }
        val intradayInfoResult = async { repository.getIntradayInfo(symbol) }

        companyInfoResult.await().fold(
            onSuccess = {
                state = state.copy(
                    companyInfo = it,
                    isLoading = false,
                    error = null
                )
            },
            onFailure = {
                state = state.copy(
                    companyInfo = null,
                    isLoading = false,
                    error = it.message
                )
            }
        )

        intradayInfoResult.await().fold(
            onSuccess = {
                state = state.copy(
                    stockInfos = it,
                    isLoading = false,
                    error = null
                )
            },
            onFailure = {
                state = state.copy(
                    stockInfos = emptyList(),
                    isLoading = false,
                    error = it.message
                )
            }
        )
    }
}