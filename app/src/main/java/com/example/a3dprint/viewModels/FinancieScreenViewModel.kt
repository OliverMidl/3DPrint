package com.example.a3dprint.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FinancieScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val zakazkaDao = AppDatabase.getDatabase(application).zakazkaDao()

    private val _totalProfit = MutableStateFlow(0.0)
    val totalProfit: StateFlow<Double> = _totalProfit

    init {
        loadTotalProfit()
    }

    private fun loadTotalProfit() {
        viewModelScope.launch {
            _totalProfit.value = zakazkaDao.getTotalPrice() ?: 0.0
        }
    }
}