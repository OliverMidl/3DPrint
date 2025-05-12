package com.example.a3dprint.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Hlavná trieda Room databázy.
 *
 * Táto databáza obsahuje dve entity(tabuľky): Filament a Zakazka.
 */
@Database(
    entities = [Filament::class, Zakazka::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Získava DAO objekt pre Filament.
     *
     * @return Inštancia FilamentDao pre prácu s filamentmi.
     */
    abstract fun filamentDao(): FilamentDao

    /**
     * Získava DAO objekt pre Zakazka.
     *
     * @return Inštancia ZakazkaDao pre prácu so zákazkami.
     */
    abstract fun zakazkaDao(): ZakazkaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Získava inštanciu databázy.
         * Ak inštancia ešte neexistuje, vytvorí novú.
         *
         * @param context Kontext aplikácie.
         * @return Inštancia AppDatabase.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database_3d"
                )
                    .build().also { INSTANCE = it }
            }
        }
    }
}