package com.inspiredcoda.farmerscommunity.presentation.screen.farmer_list

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.inspiredcoda.farmerscommunity.di.ServiceLocator
import com.inspiredcoda.farmerscommunity.domain.GetFarmerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FarmerListViewModel(
    private val getFarmerUseCase: GetFarmerUseCase
) : ViewModel() {

    private var _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var _farmersList: MutableStateFlow<List<FarmerUiData>> = MutableStateFlow(
        mutableStateListOf()
    )
    val farmersList: StateFlow<List<FarmerUiData>> = _farmersList.asStateFlow()

    fun getFarmers() {
        viewModelScope.launch {
            getFarmerUseCase.invoke().collectLatest {
                _isLoading.value = true
                _farmersList.value = it.map { farmer ->
                    FarmerUiData(
                        id = farmer.id,
                        name = farmer.firstName,
                        crop = farmer.crop,
                        abbreviation = "${farmer.firstName.take(1)}${farmer.lastName.take(1)}"
                    )
                }
                _isLoading.value = false
            }
        }
    }

    data class FarmerUiData(
        val id: Int,
        val name: String,
        val crop: String,
        val abbreviation: String
    )

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    val getFarmerUseCase = ServiceLocator.provideGetFarmerUseCase(context)
                    FarmerListViewModel(getFarmerUseCase)
                }
            }
        }
    }

}