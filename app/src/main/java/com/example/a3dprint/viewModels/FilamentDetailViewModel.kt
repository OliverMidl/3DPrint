package com.example.a3dprint.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.AppDatabase
import com.example.a3dprint.data.Filament
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FilamentDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val filamentDao = AppDatabase.getDatabase(application).filamentDao()

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
