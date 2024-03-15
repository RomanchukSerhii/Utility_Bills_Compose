package com.serhiiromanchuk.utilitybills.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.serhiiromanchuk.utilitybills.data.dao.BillDao
import com.serhiiromanchuk.utilitybills.data.dao.BillPackageDao
import com.serhiiromanchuk.utilitybills.data.dao.UtilityServiceDao
import com.serhiiromanchuk.utilitybills.data.dbmodel.BillDbModel
import com.serhiiromanchuk.utilitybills.data.dbmodel.BillPackageDbModel
import com.serhiiromanchuk.utilitybills.data.dbmodel.LocalDateConverter
import com.serhiiromanchuk.utilitybills.data.dbmodel.UtilityServiceDbModel

@Database(
    entities = [UtilityServiceDbModel::class, BillDbModel::class, BillPackageDbModel::class],
    version = 10,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun utilityServiceDao(): UtilityServiceDao

    abstract fun billDao(): BillDao

    abstract fun billPackageDao(): BillPackageDao

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