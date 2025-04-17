package com.example.a3dprint.data

class ZakazkaRepository(private val dao: ZakazkaDao) {
    val allZakazka = dao.getAllZakazka()
    suspend fun insert(zakazka: Zakazka) {
        dao.insert(zakazka)
    }
}