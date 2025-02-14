package com.inspiredcoda.farmerscommunity.presentation.screen.add_farmer

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.inspiredcoda.farmerscommunity.di.ServiceLocator
import com.inspiredcoda.farmerscommunity.domain.AddFarmerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddFarmerViewModel(
    private val addFarmerUseCase: AddFarmerUseCase
) : ViewModel() {

    private var _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _firstName: MutableStateFlow<String> = MutableStateFlow("")
    var firstName: StateFlow<String> = _firstName.asStateFlow()

    private val _lastName: MutableStateFlow<String> = MutableStateFlow("")
    var lastName: StateFlow<String> = _lastName.asStateFlow()

    private val _farmerCrop: MutableStateFlow<String> = MutableStateFlow("")
    var farmerCrop: StateFlow<String> = _farmerCrop.asStateFlow()

    private var _uiState: MutableStateFlow<AddFarmerUiState?> = MutableStateFlow(null)
    val uiState: StateFlow<AddFarmerUiState?> = _uiState.asStateFlow()

    private var _validationError: MutableStateFlow<ValidationResult?> = MutableStateFlow(null)
    val validationError: StateFlow<ValidationResult?> = _validationError.asStateFlow()

    val cropTypes = listOf("Cereals", "Legumes", "Wheat", "Tubers", "Grains", "Vegetables", "Fruits")


    fun validateFarmerInputs() {
        viewModelScope.launch {
            _isLoading.value = true
            val validationResult = runInputValidations()
            if (!validationResult.isSuccess()) {
                Log.d("XXXX::::", "validateFarmerInputs: Validation Failed")
                _validationError.value = validationResult
                _isLoading.value = false
                return@launch
            }

            val isAddedSuccessfully = addFarmerUseCase.invoke(
                firstName = firstName.value,
                lastName = lastName.value,
                crop = farmerCrop.value
            )

            if (!isAddedSuccessfully) {
                _uiState.value = AddFarmerUiState.Error("Failed to add farmer")
                _isLoading.value = false
                return@launch
            }

            _uiState.value = AddFarmerUiState.FarmerAdded
            _isLoading.value = false
        }
    }

    private fun runInputValidations(): ValidationResult {
        val validationResult = ValidationResult()

        validationResult.firstNameError = if (firstName.value.isNullOrBlank()) {
            "First name is required"
        }else {
            null
        }

        validationResult.lastNameError = if (lastName.value.isNullOrBlank()) {
            "Last name is required"
        } else {
            null
        }

        validationResult.cropError = if (farmerCrop.value.isNullOrBlank()) {
            "Crop is required"
        } else {
            null
        }

        return validationResult
    }

    fun onFirstNameChanged(firstName: String) {
        _firstName.value = firstName
    }

    fun onLastNameChanged(lastName: String) {
        _lastName.value = lastName
    }

    fun onCropChanged(crop: String) {
        _farmerCrop.value = crop
    }

    data class ValidationResult(
        var firstNameError: String? = null,
        var lastNameError: String? = null,
        var cropError: String? = null
    ) {
        fun isSuccess(): Boolean {
            return firstNameError == null && lastNameError == null && cropError == null
        }
    }

    sealed interface AddFarmerUiState {
        object FarmerAdded: AddFarmerUiState
        data class Error(val message: String): AddFarmerUiState
    }

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    val addFarmerUseCase = ServiceLocator.provideAddFarmerUseCase(context)
                    AddFarmerViewModel(addFarmerUseCase)
                }
            }
        }
    }

}