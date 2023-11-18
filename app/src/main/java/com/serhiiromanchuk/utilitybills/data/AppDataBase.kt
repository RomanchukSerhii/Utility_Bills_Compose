package com.serhiiromanchuk.utilitybills.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.serhiiromanchuk.utilitybills.data.dao.UtilityServiceDao
import com.serhiiromanchuk.utilitybills.data.dbmodel.UtilityServiceItemDbModel

@Database(entities = [UtilityServiceItemDbModel::class], version = 2, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun utilityServiceDao(): UtilityServiceDao

    companion object {
        private var INSTANCE: AppDataBase? = null
        private val LOCK: Any = Any()
        private const val DB_NAME = "utility_service.db"

        fun getInstance(application: Application): AppDataBase {
            INSTANCE?.let { return it }

            synchronized(LOCK) {
                INSTANCE?.let { return it }
                val instance = Room.databaseBuilder(
                    application,
                    AppDataBase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}