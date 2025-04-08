package com.dvidal.stockmarketapp.presentation.company_listings

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvidal.stockmarketapp.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val repository: StockRepository
): ViewModel() {

    var state by mutableStateOf(CompanyListingsState())
        private set

    private var searchJob: Job? = null

    fun onEvent(event: CompanyListingsEvent) {
        when(event) {
            is CompanyListingsEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)

                searchJob?.cancel()
                searchJob = getCompanyListings(query = event.query, forceRefresh = true)
            }
            is CompanyListingsEvent.Refresh -> {
                getCompanyListings(forceRefresh = true)
            }
        }
    }

    fun getCompanyListings(
        query: String = state.searchQuery.lowercase(),
        forceRefresh: Boolean = false
    ) = viewModelScope.launch {

        if (!forceRefresh) delay(500)

        repository.getCompanyListings(query, forceRefresh)
            .onStart { state = state.copy(isLoading = true) }
            .onCompletion { state = state.copy(isLoading = false) }
            .collect { result ->

            }
    }
}