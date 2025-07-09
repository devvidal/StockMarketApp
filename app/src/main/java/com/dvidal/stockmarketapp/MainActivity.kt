package com.dvidal.stockmarketapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavOptions
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgs
import androidx.navigation.navArgument
import com.dvidal.stockmarketapp.presentation.company_info.CompanyInfoScreen
import com.dvidal.stockmarketapp.presentation.company_info.CompanyInfoViewModel
import com.dvidal.stockmarketapp.presentation.company_listings.CompanyListingsScreen
import com.dvidal.stockmarketapp.presentation.company_listings.CompanyListingsViewModel
import com.dvidal.stockmarketapp.ui.theme.StockMarketAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockMarketAppTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        NavHost(
                            navController = navController,
                            startDestination = "CompanyListings"
                        ) {

                            composable("CompanyListings") {
                                val viewModel: CompanyListingsViewModel = hiltViewModel()
                                val state = viewModel.state

                                CompanyListingsScreen(
                                    state = state,
                                    onEvent = { viewModel.onEvent(it) },
                                    onItemClicked = {
                                        navController.navigate(
                                            route = "CompanyInfo/${it.symbol}"
                                        )
                                    }
                                )
                            }

                            composable(
                                route = "CompanyInfo/{companySymbol}",
                                arguments = listOf(
                                    navArgument("companySymbol") { type = StringType }
                                )
                            ) { backStackEntry ->
                                val viewModel: CompanyInfoViewModel = hiltViewModel()
                                val state = viewModel.state

                                CompanyInfoScreen(state)
                            }
                        }
                    }
                }
            }
        }
    }
}