package com.example.a3dprint.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.Filament
import com.example.a3dprint.data.FilamentDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FilamentDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val filamentDao: FilamentDao
) : ViewModel() {

    fun getFilamentById(id: Int): Flow<Filament?> {
        return filamentDao.getFilamentById(id)
    }

    fun deleteFilament(filamentId: Int) {
        viewModelScope.launch {
            filamentDao.deleteFilamentById(filamentId)
        }
    }

    fun updateWeight(filamentId: Int, weight: Int) {
        viewModelScope.launch {
            filamentDao.updateFilamentById(filamentId, weight)
        }
    }
}
