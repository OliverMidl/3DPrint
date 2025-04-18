package com.example.a3dprint.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ZakazkaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(zakazka: Zakazka)

    @Query("SELECT * FROM zakazky")
    fun getAllZakazka(): Flow<List<Zakazka>>

    @Query("SELECT SUM(cena) FROM zakazky")
    suspend fun getTotalPrice(): Double?

}