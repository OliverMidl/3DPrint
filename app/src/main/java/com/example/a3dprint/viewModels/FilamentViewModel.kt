package com.example.a3dprint.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.Filament
import com.example.a3dprint.data.FilamentDatabase
import com.example.a3dprint.data.FilamentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FilamentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FilamentRepository(
        FilamentDatabase.getDatabase(application).filamentDao()
    )

    val filaments = repository.allFilaments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}