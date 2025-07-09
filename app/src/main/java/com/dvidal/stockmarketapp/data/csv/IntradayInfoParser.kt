package com.dvidal.stockmarketapp.data.csv

import com.dvidal.stockmarketapp.data.mapper.toIntradayInfo
import com.dvidal.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.dvidal.stockmarketapp.domain.model.CompanyListing
import com.dvidal.stockmarketapp.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor(): CsvParser<IntradayInfo> {

    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(1) ?: return@mapNotNull null

                    IntradayInfoDto(
                        timestamp = timestamp,
                        close = close.toDouble()
                    ).toIntradayInfo()

                }
                .filter { it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth }
                .sortedBy { it.date.hour }
                .also { csvReader.close() }
        }
    }
}