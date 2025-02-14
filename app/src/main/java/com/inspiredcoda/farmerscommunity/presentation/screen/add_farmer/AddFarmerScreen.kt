package com.inspiredcoda.farmerscommunity.presentation.screen.add_farmer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.inspiredcoda.farmerscommunity.R
import com.inspiredcoda.farmerscommunity.presentation.component.StatusScreen
import com.inspiredcoda.farmerscommunity.presentation.component.TextInputWithLabel
import com.inspiredcoda.farmerscommunity.presentation.ui.theme.FarmersCommunityTheme

@Composable
fun AddFarmerScreen(
    navController: NavController,
    addFarmerViewModel: AddFarmerViewModel
) {

    AddFarmerScreenContainer(
        navController = navController,
        isLoading = addFarmerViewModel.isLoading.collectAsState(),
        firstName = addFarmerViewModel.firstName.collectAsState(),
        lastName = addFarmerViewModel.lastName.collectAsState(),
        crop = addFarmerViewModel.farmerCrop.collectAsState(),
        cropTypes = addFarmerViewModel.cropTypes,
        onFirstNameChanged = addFarmerViewModel::onFirstNameChanged,
        onLastNameChanged = addFarmerViewModel::onLastNameChanged,
        onCropChanged = addFarmerViewModel::onCropChanged,
        onContinueClicked = addFarmerViewModel::validateFarmerInputs,
        uiAction = addFarmerViewModel.uiState.collectAsState(),
        validationError = addFarmerViewModel.validationError.collectAsState()
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFarmerScreenContainer(
    navController: NavController,
    isLoading: State<Boolean>,
    firstName: State<String>,
    lastName: State<String>,
    crop: State<String>,
    cropTypes: List<String>,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onCropChanged: (String) -> Unit,
    onContinueClicked: () -> Unit,
    uiAction: State<AddFarmerViewModel.AddFarmerUiState?>,
    validationError: State<AddFarmerViewModel.ValidationResult?>
) {

    val snackBarHostState = SnackbarHostState()

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = uiAction.value) {
        when (val action = uiAction.value) {
            AddFarmerViewModel.AddFarmerUiState.FarmerAdded -> {
                showDialog = true
            }

            is AddFarmerViewModel.AddFarmerUiState.Error -> {
                snackBarHostState.showSnackbar(action.message)
            }

            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.add_a_farmer),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 16.dp,
                        end = innerPadding.calculateEndPadding(LayoutDirection.Ltr) + 16.dp
                    )
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    TextInputWithLabel(
                        title = stringResource(R.string.first_name),
                        placeholder = "e.g Hillsplay",
                        value = firstName,
                        onValueChange = onFirstNameChanged,
                        error = validationError.value?.firstNameError
                    )

                    TextInputWithLabel(
                        title = stringResource(R.string.last_name),
                        placeholder = "e.g Hillsplay",
                        value = lastName,
                        onValueChange = onLastNameChanged,
                        error = validationError.value?.lastNameError
                    )

                    TextInputWithLabel(
                        title = stringResource(R.string.crop_type),
                        placeholder = "e.g Cassava",
                        value = crop,
                        options = cropTypes,
                        onOptionClicked = onCropChanged,
                        error = validationError.value?.cropError
                    )
                }

                Button(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    onClick = onContinueClicked
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 7.dp),
                        text = stringResource(R.string.save_and_continue)
                    )
                }

            }

            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp)
                )
            }

            if (showDialog) {
                Dialog(
                    onDismissRequest = {
                        showDialog = false
                    },
                    properties = DialogProperties(
                        dismissOnClickOutside = false,
                        dismissOnBackPress = false,
                        usePlatformDefaultWidth = false
                    )
                ) {
                    StatusScreen {
                        navController.navigateUp()
                    }
                }
            }
        }

    }

}


@Preview
@Composable
fun AddFarmerScreen_Preview(modifier: Modifier = Modifier) {
    FarmersCommunityTheme {
        AddFarmerScreenContainer(
            navController = rememberNavController(),
            isLoading = remember { mutableStateOf(true) },
            firstName = remember { mutableStateOf("") },
            lastName = remember { mutableStateOf("") },
            crop = remember { mutableStateOf("") },
            cropTypes = listOf("Roots", "Vegetables", "Fruits"),
            onFirstNameChanged = {},
            onLastNameChanged = {},
            onCropChanged = {},
            onContinueClicked = {},
            uiAction = remember { mutableStateOf(null) },
            validationError = remember {
                mutableStateOf(
                    AddFarmerViewModel.ValidationResult(
                        firstNameError = null,
                        lastNameError = null,
                        cropError = null
                    )
                )
            }
        )
    }
}