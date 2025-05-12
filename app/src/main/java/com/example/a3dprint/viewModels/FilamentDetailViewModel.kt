package com.example.a3dprint.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.AppDatabase
import com.example.a3dprint.data.Filament
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * ViewModel pre detail filamentu.
 * Obsahuje metódy na získanie detailov o filamente, jeho odstránenie a aktualizáciu váhy.
 */
class FilamentDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val filamentDao = AppDatabase.getDatabase(application).filamentDao()

    /**
     * Získava filament podľa jeho ID.
     *
     * @param id ID filamentu.
     * @return Flow s detailmi filamentu alebo null, ak filament neexistuje.
     */
    fun getFilamentById(id: Int): Flow<Filament?> {
        return filamentDao.getFilamentById(id)
    }

    /**
     * Odstráni filament podľa jeho ID.
     *
     * @param filamentId ID filamentu, ktorý sa má odstrániť.
     */
    fun deleteFilament(filamentId: Int) {
        viewModelScope.launch {
            filamentDao.deleteFilamentById(filamentId)
        }
    }

    /**
     * Aktualizuje váhu filamentu podľa jeho ID.
     *
     * @param filamentId ID filamentu, ktorého váha sa má aktualizovať.
     * @param weight Nová váha filamentu.
     */
    fun updateWeight(filamentId: Int, weight: Int) {
        viewModelScope.launch {
            filamentDao.updateFilamentById(filamentId, weight)
        }
    }
}
