package com.inspiredcoda.farmerscommunity.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "farmer_entity")
data class FarmerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val crop: String
) {


    companion object {
        fun generateUserId(firstName: String, lastName: String): String {
            return "${firstName}_${lastName}"
        }
    }


}
