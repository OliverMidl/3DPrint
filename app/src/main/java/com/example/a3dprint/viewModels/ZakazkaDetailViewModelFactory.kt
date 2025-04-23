package com.example.a3dprint.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a3dprint.data.FilamentDao
import com.example.a3dprint.data.ZakazkaDao

class ZakazkaDetailViewModelFactory(
    private val zakazkaDao: ZakazkaDao,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ZakazkaDetailViewModel(zakazkaDao) as T
    }
}