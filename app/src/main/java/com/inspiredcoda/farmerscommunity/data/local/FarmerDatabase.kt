package com.inspiredcoda.farmerscommunity.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.inspiredcoda.farmerscommunity.data.local.model.FarmerEntity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(
    entities = [FarmerEntity::class],
    exportSchema = true,
    version = 1
)
abstract class FarmerDatabase: RoomDatabase() {

    abstract fun farmerDao(): FarmerDao

    companion object {
        @OptIn(InternalCoroutinesApi::class)
        fun create(context: Context) = synchronized(true) {
            Room.databaseBuilder(
                context = context,
                klass = FarmerDatabase::class.java,
                name = "farmer_db"
            )
                .build()
        }
    }

}