package com.inspiredcoda.farmerscommunity.domain

import com.inspiredcoda.farmerscommunity.data.local.FarmerDao
import com.inspiredcoda.farmerscommunity.data.local.model.FarmerEntity

class AddFarmerUseCase(
    private val farmerDao: FarmerDao
) {

    suspend fun invoke(
        firstName: String,
        lastName: String,
        crop: String
    ): Boolean {
        return farmerDao.insertFarmer(
            FarmerEntity(
                firstName = firstName,
                lastName = lastName,
                crop = crop
            )
        ) > 0
    }

}