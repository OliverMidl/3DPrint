package com.example.a3dprint.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FilamentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(filament: Filament)

    @Query("SELECT * FROM filaments")
    fun getAllFilaments(): Flow<List<Filament>>
}