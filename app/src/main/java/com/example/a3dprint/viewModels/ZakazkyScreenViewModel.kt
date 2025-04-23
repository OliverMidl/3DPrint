package com.example.a3dprint.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.AppDatabase
import com.example.a3dprint.data.Filament
import com.example.a3dprint.data.FilamentRepository
import com.example.a3dprint.data.Zakazka
import com.example.a3dprint.data.ZakazkaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ZakazkyScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ZakazkaRepository(
        AppDatabase.getDatabase(application).zakazkaDao()
    )

    val zakazky = repository.allZakazka
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getZakazkatById(id: Int): Flow<Zakazka?> {
        return repository.getDao.getZakazkaById(id)
        //return filamentDao.getFilamentById(id)
    }
}

