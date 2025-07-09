package com.dvidal.stockmarketapp.di

import android.app.Application
import androidx.room.Room
import com.dvidal.stockmarketapp.data.local.StockDao
import com.dvidal.stockmarketapp.data.local.StockDatabase
import com.dvidal.stockmarketapp.data.remote.StockApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockApi {
        val converterFactory = GsonConverterFactory.create(GsonBuilder().create())

        return Retrofit.Builder()
            .baseUrl(StockApi.BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDao(
        app: Application
    ): StockDao {
        return Room.databaseBuilder(
            context = app,
            klass = StockDatabase::class.java,
            name = "stockmarket.db"
        ).build().dao
    }
}