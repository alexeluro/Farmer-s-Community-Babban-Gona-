package com.inspiredcoda.farmerscommunity.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.inspiredcoda.farmerscommunity.data.local.model.FarmerEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FarmerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFarmer(farmerEntity: FarmerEntity): Long

    @Query("SELECT * FROM farmer_entity")
    fun getAllFarmers(): Flow<List<FarmerEntity>>

    @Delete
    suspend fun deleteFarmer(farmerEntity: FarmerEntity): Int

    @Query("DELETE FROM farmer_entity")
    suspend fun deleteAllFarmers(): Int

    @Query("DELETE FROM farmer_entity WHERE id = :id")
    suspend fun deleteFarmer(id: String): Int

}