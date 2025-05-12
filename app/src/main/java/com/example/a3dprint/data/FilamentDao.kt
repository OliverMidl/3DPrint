package com.example.a3dprint.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlin.collections.List


/**
 * DAO pre prácu s tabuľkou Filament.
 */
@Dao
interface FilamentDao {
    /**
     * Vloží alebo nahradí filament v databáze.
     *
     * Ak už existuje filament s rovnakým ID, nahradí sa.
     *
     * @param filament Objekt filamentu, ktorý sa má uložiť.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(filament: Filament)

    /**
     * Získa všetky filamenty uložené v databáze ako Flow.
     *
     * @return Flow zoznam všetkých filamentov.
     */
    @Query("SELECT * FROM filaments")
    fun getAllFilaments(): Flow<List<Filament>>

    /**
     * Získa filament podľa jeho ID.
     *
     * @param id ID požadovaného filamentu.
     * @return Flow s filamentom alebo null, ak sa nenašiel.
     */
    @Query("SELECT * FROM filaments WHERE id = :id")
    fun getFilamentById(id: Int): Flow<Filament?>

    /**
     * Vráti počet všetkých filamentov v databáze.
     *
     * @return Celkový počet filamentov.
     */
    @Query("SELECT count(*) FROM filaments")
    fun getCount(): Int

    /**
     * Odstráni filament z databázy podľa jeho ID.
     *
     * @param filamentId ID filamentu, ktorý sa má odstrániť.
     */
    @Query("DELETE FROM filaments WHERE id = :filamentId")
    suspend fun deleteFilamentById(filamentId: Int)

    /**
     * Pripočíta váhu k aktuálnej hmotnosti filamentu podľa jeho ID.
     *
     * @param filamentId ID filamentu, ktorý sa má aktualizovať.
     * @param weight Hmotnosť, ktorá sa má pripočítať.
     */
    @Query("UPDATE filaments SET currentWeight = currentWeight + :weight WHERE id = :filamentId")
    suspend fun updateFilamentById(filamentId: Int, weight: Int)


}