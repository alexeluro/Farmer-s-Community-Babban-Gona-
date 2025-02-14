package com.inspiredcoda.farmerscommunity.presentation.screen.farmer_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.inspiredcoda.farmerscommunity.R
import com.inspiredcoda.farmerscommunity.presentation.navigation.Routes
import com.inspiredcoda.farmerscommunity.presentation.ui.theme.FarmersCommunityTheme

@Composable
fun FarmerListScreen(
    navController: NavController,
    farmerListViewModel: FarmerListViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val stateFlow = lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        farmerListViewModel.getFarmers()
    }

    FarmerScreenContainer(
        navController = navController,
        isLoading = farmerListViewModel.isLoading.collectAsState(),
        farmers = farmerListViewModel.farmersList.collectAsState()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmerScreenContainer(
    navController: NavController,
    isLoading: State<Boolean>,
    farmers: State<List<FarmerListViewModel.FarmerUiData>>
) {
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.farmers_community),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                expanded = lazyListState.lastScrolledForward,
                text = {
                    Text(text = stringResource(R.string.add_farmer))
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                },
                onClick = {
                    navController.navigate(Routes.AddFarmerScreen)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding()
                )
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                ),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 7.dp
                    ),
                state = lazyListState
            ) {
                items(farmers.value) { farmer ->
                    FarmerItem(farmer = farmer)
                }
            }

            if (farmers.value.isEmpty()) {
                Text(text = stringResource(R.string.no_farmers_found))
            }

            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp)
                )
            }
        }

    }
}

@Composable
fun FarmerItem(farmer: FarmerListViewModel.FarmerUiData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 7.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(7.dp)
            )
            .padding(horizontal = 10.dp, vertical = 16.dp)
            .clip(RoundedCornerShape(7.dp))
    ) {

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f))
                .padding(10.dp)
        ) {

            Text(
                text = farmer.abbreviation,
                style = MaterialTheme.typography.titleMedium
            )

        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Text(
                text = farmer.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = farmer.crop,
                style = MaterialTheme.typography.bodyMedium
            )

        }


    }
}

@Preview
@Composable
private fun FarmerListScreen_Preview() {
    FarmersCommunityTheme {
        FarmerScreenContainer(
            navController = rememberNavController(),
            isLoading = remember {
                mutableStateOf(true)
            },
            farmers = remember {
                mutableStateOf(
                    listOf(
                        FarmerListViewModel.FarmerUiData(
                            id = 1,
                            name = "John Williams",
                            abbreviation = "JW",
                            crop = "Cassava"
                        ),
                        FarmerListViewModel.FarmerUiData(
                            id = 2,
                            name = "John Williams",
                            abbreviation = "JW",
                            crop = "Cassava"
                        )
                    )
                )
            }
        )
    }
}