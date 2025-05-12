package com.example.a3dprint.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.AppDatabase
import com.example.a3dprint.data.Filament
import com.example.a3dprint.data.FilamentRepository
import com.example.a3dprint.data.ZakazkaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel pre filamnty.
 */
class FilamentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FilamentRepository(
       AppDatabase.getDatabase(application).filamentDao()
    )

    /**
     * Zoznam všetkých filamentov z databázy.
     * Poskytuje zoznam všetkých filamentov.
     */
    val filaments = repository.allFilaments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())



}