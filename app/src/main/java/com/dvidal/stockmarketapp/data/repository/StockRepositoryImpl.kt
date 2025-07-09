package com.dvidal.stockmarketapp.data.repository

import android.util.Printer
import com.dvidal.stockmarketapp.data.csv.CsvParser
import com.dvidal.stockmarketapp.data.local.StockDao
import com.dvidal.stockmarketapp.data.mapper.toCompanyInfo
import com.dvidal.stockmarketapp.data.mapper.toDomain
import com.dvidal.stockmarketapp.data.mapper.toEntity
import com.dvidal.stockmarketapp.data.remote.StockApi
import com.dvidal.stockmarketapp.domain.model.CompanyInfo
import com.dvidal.stockmarketapp.domain.model.CompanyListing
import com.dvidal.stockmarketapp.domain.model.IntradayInfo
import com.dvidal.stockmarketapp.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val dao: StockDao,
    private val companyListingsParser: CsvParser<CompanyListing>,
    private val intradayInfoParser: CsvParser<IntradayInfo>
): StockRepository {

    override suspend fun getCompanyListings(
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Result<List<CompanyListing>>> {
        return flow {
            val localListings = dao.searchCompanyListing(query).map { it.toDomain() }
            emit(Result.success(localListings))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                return@flow
            }

            runCatching { api.getListings() }.fold(
                onSuccess = { response ->
                    val responseMapped = companyListingsParser.parse(response.byteStream())

                    // db executions
                    dao.clearCompanyListings()
                    dao.insertCompanyListings(responseMapped.map { it.toEntity() })
                    val entities = dao.searchCompanyListing().map { it.toDomain() }
                    emit(Result.success(entities))
                },
                onFailure = {
                    emit(Result.failure<List<CompanyListing>>(Exception("Couldn't load data.")))
                }
            )
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Result<List<IntradayInfo>> {

        runCatching { api.getIntradayInfo(symbol) }.fold(
            onSuccess = { result ->
                val results = intradayInfoParser.parse(result.byteStream())
                return Result.success(results)
            },
            onFailure = {
                return Result.failure<List<IntradayInfo>>(Exception("Couldn't intraday info."))
            }
        )
    }

    override suspend fun getCompanyInfo(symbol: String): Result<CompanyInfo> {

        runCatching { api.getCompanyInfo(symbol) }.fold(
            onSuccess = { result ->
                return Result.success(result.toCompanyInfo())
            },
            onFailure = { throwable ->
                return Result.failure<CompanyInfo>(Exception("Couldn't company info."))
            }
        )
    }
}