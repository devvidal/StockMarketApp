package com.dvidal.stockmarketapp.data.mapper

import com.dvidal.stockmarketapp.data.local.CompanyListingEntity
import com.dvidal.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.dvidal.stockmarketapp.domain.model.CompanyInfo
import com.dvidal.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toDomain(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol.orEmpty(),
        description = description.orEmpty(),
        name = name.orEmpty(),
        country = country.orEmpty(),
        industry = industry.orEmpty()
    )
}