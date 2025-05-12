package com.example.a3dprint.data

/**
 * Repozitár pre prácu s entitou Zakazka.
 * Slúži ako vrstva medzi databázou (DAO) a ViewModelom.
 *
 * @param dao DAO objekt, ktorý zabezpečuje prístup k databáze.
 */
class ZakazkaRepository(private val dao: ZakazkaDao) {
    /**
     * zoznam všetkých zakázok.
     */
    val allZakazka = dao.getAllZakazka()

    /**
     * Uloží alebo aktualizuje zakázku do databázy.
     *
     * @param zakazka Objekt zakázky, ktorý sa má uložiť.
     */
    suspend fun insert(zakazka: Zakazka) {
        dao.insert(zakazka)
    }

    /**
     * Získa súčet ceny všetkých zakázok z databázy.
     *
     * @return Celková cena zakázok.
     */
    suspend fun getTotalPrice(): Double? {
        return dao.getTotalPrice()
    }
}