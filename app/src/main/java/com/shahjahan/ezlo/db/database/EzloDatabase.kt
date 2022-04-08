package com.shahjahan.ezlo.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shahjahan.ezlo.models.Device

@Database(entities = [Device::class], version = 1, exportSchema = false)
abstract class EzloDatabase : RoomDatabase() {
    abstract val mainDao: com.shahjahan.ezlo.db.dao.MainDao
}

private lateinit var INSTANCE: EzloDatabase

const val DB_NAME: String = "ezlo_db"

fun getDatabase(context: Context): EzloDatabase {
    synchronized(EzloDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                EzloDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
    return INSTANCE
}





