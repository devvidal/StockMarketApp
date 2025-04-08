@file:OptIn(ExperimentalMaterial3Api::class)

package com.dvidal.stockmarketapp.presentation.company_listings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dvidal.stockmarketapp.presentation.company_listings.CompanyListingsEvent
import com.dvidal.stockmarketapp.presentation.company_listings.CompanyListingsViewModel


@Composable
fun CompanyListingsScreen(
    viewModel: CompanyListingsViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { query ->
                viewModel.onEvent(CompanyListingsEvent.OnSearchQueryChange(query))
            },
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(state.companies) { index, company ->
                CompanyItem(
                    company = company,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            TODO("Navigate to detail screen")
                        }
                )

                if (index < state.companies.size) {
                    Divider(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}