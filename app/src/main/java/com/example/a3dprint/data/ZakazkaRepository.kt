package com.example.a3dprint.data

class ZakazkaRepository(private val dao: ZakazkaDao) {
    val getDao = dao //kontrola
    val allZakazka = dao.getAllZakazka()
    suspend fun insert(zakazka: Zakazka) {
        dao.insert(zakazka)
    }
}