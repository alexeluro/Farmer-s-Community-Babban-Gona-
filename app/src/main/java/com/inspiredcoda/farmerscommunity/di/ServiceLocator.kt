package com.inspiredcoda.farmerscommunity.di

import android.content.Context
import androidx.savedstate.SavedStateRegistry
import com.inspiredcoda.farmerscommunity.data.local.FarmerDao
import com.inspiredcoda.farmerscommunity.data.local.FarmerDatabase
import com.inspiredcoda.farmerscommunity.domain.AddFarmerUseCase
import com.inspiredcoda.farmerscommunity.domain.GetFarmerUseCase

object ServiceLocator {

    private fun provideDatabase(context: Context): FarmerDatabase {
        return FarmerDatabase.create(context.applicationContext)
    }

    fun provideFarmerDao(context: Context): FarmerDao {
        return provideDatabase(context).farmerDao()
    }

    fun provideGetFarmerUseCase(context: Context): GetFarmerUseCase {
        val farmerDao = provideFarmerDao(context)
        return GetFarmerUseCase(farmerDao)
    }

    fun provideAddFarmerUseCase(context: Context): AddFarmerUseCase {
        val farmerDao = provideFarmerDao(context)
        return AddFarmerUseCase(farmerDao)
    }

}