package com.example.a3dprint.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.AppDatabase
import com.example.a3dprint.data.FilamentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn


class FilamentViewModel(application: Application) : AndroidViewModel(application) {


    private val repository = FilamentRepository(
        AppDatabase.getDatabase(application).filamentDao()
    )

    val filaments = repository.allFilaments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}