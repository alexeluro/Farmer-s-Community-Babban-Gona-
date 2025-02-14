package com.inspiredcoda.farmerscommunity.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.inspiredcoda.farmerscommunity.presentation.screen.add_farmer.AddFarmerScreen
import com.inspiredcoda.farmerscommunity.presentation.screen.add_farmer.AddFarmerViewModel
import com.inspiredcoda.farmerscommunity.presentation.screen.farmer_list.FarmerListScreen
import com.inspiredcoda.farmerscommunity.presentation.screen.farmer_list.FarmerListViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Routes.FarmerListScreen) {
        composable<Routes.FarmerListScreen> {
            val farmerListViewModel = viewModel<FarmerListViewModel>(
                factory = FarmerListViewModel.factory(LocalContext.current.applicationContext)
            )
            FarmerListScreen(
                navController = navController,
                farmerListViewModel = farmerListViewModel
            )
        }

        composable<Routes.AddFarmerScreen> {
            val addFarmerViewModel: AddFarmerViewModel = viewModel(
                factory = AddFarmerViewModel.factory(LocalContext.current.applicationContext)
            )
            AddFarmerScreen(
                navController = navController,
                addFarmerViewModel = addFarmerViewModel
            )
        }


    }
}