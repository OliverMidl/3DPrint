package com.example.a3dprint.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FinancieScreenViewModel : ViewModel() {

    private val _totalProfit = MutableStateFlow(0.0)
    val totalProfit: StateFlow<Double> = _totalProfit

    init {
        // V budúcnosti môžeš načítať z databázy alebo repository
        loadTotalProfit()
    }

    private fun loadTotalProfit() {
        viewModelScope.launch {
            // Simulácia načítania dát
            _totalProfit.value = 0.0  // napr. db.getTotalProfit()
        }
    }

    fun updateProfit(newProfit: Double) {
        _totalProfit.value = newProfit
    }
}