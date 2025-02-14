package com.inspiredcoda.farmerscommunity.domain

import com.inspiredcoda.farmerscommunity.data.local.FarmerDao

class GetFarmerUseCase(
    private val farmerDao: FarmerDao
) {
    fun invoke() = farmerDao.getAllFarmers()
}