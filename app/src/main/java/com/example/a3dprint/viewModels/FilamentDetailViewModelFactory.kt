package com.example.a3dprint.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a3dprint.data.FilamentDao

class FilamentDetailViewModelFactory(
    private val filamentDao: FilamentDao,
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FilamentDetailViewModel(savedStateHandle, filamentDao) as T
    }
}