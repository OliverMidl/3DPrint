package com.example.a3dprint.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO pre tabuľku zakázok.
 */
@Dao
interface ZakazkaDao {
    /**
     * Vloží alebo aktualizuje zakázku do databázy.
     *
     * @param zakazka Objekt zakázky na uloženie.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(zakazka: Zakazka)

    /**
     * Získa všetky zakázky.
     *
     * @return Flow s listom všetkých zakázok.
     */
    @Query("SELECT * FROM zakazky")
    fun getAllZakazka(): Flow<List<Zakazka>>

    /**
     * Získa počet všetkých zakázok v databáze.
     *
     * @return Počet zakázok ako celé číslo.
     */
    @Query("SELECT count(*) FROM zakazky")
    fun getCount(): Int

    /**
     * Spočíta celkovú cenu všetkých zakázok.
     *
     * @return Súčet ceny všetkých zakázok.
     */
    @Query("SELECT sum(cena) FROM zakazky")
    fun getSumPrize(): Double

    /**
     * Asynchrónne získa súčet ceny všetkých zakázok.
     *
     * @return Súčet ceny.
     */
    @Query("SELECT SUM(cena) FROM zakazky")
    suspend fun getTotalPrice(): Double?

    /**
     * Získa konkrétnu zakázku podľa jej ID.
     *
     * @param id Identifikátor zakázky.
     * @return Flow s nájdenou zakázkou.
     */
    @Query("SELECT * FROM zakazky WHERE id = :id")
    fun getZakazkaById(id: Int): Flow<Zakazka?>

    /**
     * Odstráni zakázku z databázy podľa jej ID.
     *
     * @param id Identifikátor zakázky, ktorú chceme odstrániť.
     */
    @Query("DELETE FROM zakazky  WHERE id = :id")
    suspend fun deleteZakazkaById(id: Int)

}