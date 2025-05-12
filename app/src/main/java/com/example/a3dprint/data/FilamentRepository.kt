package com.example.a3dprint.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository pre prácu s filamentmi.
 *
 * Slúži ako sprostredkovateľ medzi databázou a ViewModelom.
 * Umožňuje získavať všetky filamenty a vkladať nové filamenty.
 *
 * @property dao DAO objekt pre prístup k údajom o filamentoch.
 */
class FilamentRepository(private val dao: FilamentDao) {
    /**
     * Stream všetkých filamentov uložených v databáze.
     *
     * Tento prúd sa automaticky aktualizuje pri zmene dát.
     */
    val allFilaments = dao.getAllFilaments()

    /**
     * Vloží nový filament do databázy.
     *
     * @param filament Filament, ktorý sa má pridať.
     */
    suspend fun insert(filament: Filament) {
        dao.insert(filament)
    }

}