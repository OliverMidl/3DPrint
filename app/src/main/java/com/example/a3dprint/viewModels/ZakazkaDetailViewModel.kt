package com.example.a3dprint.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.AppDatabase
import com.example.a3dprint.data.Zakazka
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * ViewModel pre detail zakázky. Poskytuje funkcie na získanie detailov zakázky
 *
 * @param application Aplikácia, ktorá poskytuje kontext pre databázu.
 */
class ZakazkaDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val zakazkaDao = AppDatabase.getDatabase(application).zakazkaDao()

    /**
     * Získava detail zakázky na základe jej ID.
     *
     * @param id ID zakázky, ktorá sa má získať.
     * @return Flow obsahujúci detail zakázky alebo null, ak zakázka neexistuje.
     */
    fun getZakazkaById(id: Int): Flow<Zakazka?> {
        return zakazkaDao.getZakazkaById(id)
    }

    /**
     * Vymaže zakázku na základe jej ID.
     *
     * @param zakazkaId ID zakázky, ktorá sa má vymazať.
     */
    fun deleteZakazka(zakazkaId: Int) {
        viewModelScope.launch {
            zakazkaDao.deleteZakazkaById(zakazkaId)
        }
    }


}