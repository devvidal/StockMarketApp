package com.dvidal.stockmarketapp.data.repository

import com.dvidal.stockmarketapp.data.local.StockDao
import com.dvidal.stockmarketapp.data.remote.StockApi
import com.dvidal.stockmarketapp.domain.model.CompanyListing
import com.dvidal.stockmarketapp.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val dao: StockDao
): StockRepository {

    override suspend fun getCompanyListings(
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Result<List<CompanyListing>>> {
        return flow {

        }
    }
}