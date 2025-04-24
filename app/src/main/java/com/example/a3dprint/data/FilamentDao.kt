package com.example.a3dprint.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlin.collections.List

@Dao
interface FilamentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(filament: Filament)

    @Query("SELECT * FROM filaments")
    fun getAllFilaments(): Flow<List<Filament>>

    @Query("SELECT * FROM filaments WHERE id = :id")
    fun getFilamentById(id: Int): Flow<Filament?>

    @Query("SELECT count(*) FROM filaments")
    fun getCount(): Int

    @Query("DELETE FROM filaments WHERE id = :filamentId")
    suspend fun deleteFilamentById(filamentId: Int)

    @Query("UPDATE filaments SET currentWeight = currentWeight + :weight WHERE id = :filamentId")
    suspend fun updateFilamentById(filamentId: Int, weight: Int)


}