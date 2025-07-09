package com.dvidal.stockmarketapp.domain.repository

import com.dvidal.stockmarketapp.domain.model.CompanyInfo
import com.dvidal.stockmarketapp.domain.model.CompanyListing
import com.dvidal.stockmarketapp.domain.model.IntradayInfo
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        query: String,
        fetchFromRemote: Boolean = false
    ): Flow<Result<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol: String
    ): Result<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Result<CompanyInfo>
}