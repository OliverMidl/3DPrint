package com.example.a3dprint.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.Filament
import com.example.a3dprint.data.FilamentDao
import com.example.a3dprint.data.Zakazka
import com.example.a3dprint.data.ZakazkaDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ZakazkaDetailViewModel(
    //private val savedStateHandle: SavedStateHandle,
    private val zakazkaDao: ZakazkaDao
) : ViewModel() {


    fun getZakazkaById(id: Int): Flow<Zakazka?> {
        return zakazkaDao.getZakazkaById(id)
    }

    fun deleteZakazka(zakazkaId: Int) {
        viewModelScope.launch {
            zakazkaDao.deleteZakazkaById(zakazkaId)
        }
    }


}