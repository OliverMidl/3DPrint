package com.example.a3dprint.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.AppDatabase
import com.example.a3dprint.data.ZakazkaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel pre obrazovku Financie.
 */
class FinancieScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val repository = ZakazkaRepository(database.zakazkaDao())

    private val _totalProfit = MutableStateFlow(0.0)

    /**
     * Flow obsahujúci celkový zisk.
     * Tento flow poskytuje aktuálnu hodnotu celkového zisku, ktorý je aktualizovaný pri načítaní z databázy.
     */
    val totalProfit: StateFlow<Double> = _totalProfit

    /**
     * Pri vytvorení inštancií sa zavolá, aby načítal celkový profit.
     */
    init {
        loadTotalProfit()
    }

    private fun loadTotalProfit() {
        viewModelScope.launch {
            _totalProfit.value = repository.getTotalPrice() ?: 0.0
        }
    }
}