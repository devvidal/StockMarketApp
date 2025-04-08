package com.dvidal.stockmarketapp.domain.repository

import com.dvidal.stockmarketapp.domain.model.CompanyListing
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        query: String,
        fetchFromRemote: Boolean = false
    ): Flow<Result<List<CompanyListing>>>
}