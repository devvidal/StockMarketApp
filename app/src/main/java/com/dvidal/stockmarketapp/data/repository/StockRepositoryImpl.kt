package com.dvidal.stockmarketapp.data.repository

import com.dvidal.stockmarketapp.data.csv.CsvParser
import com.dvidal.stockmarketapp.data.local.StockDao
import com.dvidal.stockmarketapp.data.mapper.toDomain
import com.dvidal.stockmarketapp.data.mapper.toEntity
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
    private val dao: StockDao,
    private val companyListingsParser: CsvParser<CompanyListing>
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
}