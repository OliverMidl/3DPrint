package com.example.a3dprint.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.AppDatabase
import com.example.a3dprint.data.Zakazka
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ZakazkaDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val zakazkaDao = AppDatabase.getDatabase(application).zakazkaDao()

    fun getZakazkaById(id: Int): Flow<Zakazka?> {
        return zakazkaDao.getZakazkaById(id)
    }

    fun deleteZakazka(zakazkaId: Int) {
        viewModelScope.launch {
            zakazkaDao.deleteZakazkaById(zakazkaId)
        }
    }


}