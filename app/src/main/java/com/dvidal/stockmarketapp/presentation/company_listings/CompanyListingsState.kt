package com.dvidal.stockmarketapp.presentation.company_listings

import com.dvidal.stockmarketapp.domain.model.CompanyListing
import java.util.Collections.emptyList

data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)