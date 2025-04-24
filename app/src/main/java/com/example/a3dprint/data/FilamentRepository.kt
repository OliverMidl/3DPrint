package com.example.a3dprint.data

class FilamentRepository(private val dao: FilamentDao) {
    val allFilaments = dao.getAllFilaments()

    suspend fun insert(filament: Filament) {
        dao.insert(filament)
    }

}