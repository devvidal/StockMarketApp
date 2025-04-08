package com.dvidal.stockmarketapp.di

import com.dvidal.stockmarketapp.data.csv.CompanyListingsParser
import com.dvidal.stockmarketapp.data.csv.CsvParser
import com.dvidal.stockmarketapp.data.repository.StockRepositoryImpl
import com.dvidal.stockmarketapp.domain.model.CompanyListing
import com.dvidal.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ): CsvParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}