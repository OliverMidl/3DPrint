package com.example.a3dprint.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Filament::class], version = 1, exportSchema = false)
abstract class FilamentDatabase : RoomDatabase() {
    abstract fun filamentDao(): FilamentDao

    companion object {
        @Volatile
        private var INSTANCE: FilamentDatabase? = null

        fun getDatabase(context: Context): FilamentDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    FilamentDatabase::class.java,
                    "filament_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}