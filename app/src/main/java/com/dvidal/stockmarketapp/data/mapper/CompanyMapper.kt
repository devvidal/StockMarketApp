package com.dvidal.stockmarketapp.data.mapper

import com.dvidal.stockmarketapp.data.local.CompanyListingEntity
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