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
        //db
        loadTotalProfit()
    }

    private fun loadTotalProfit() {
        viewModelScope.launch {
            //data
            _totalProfit.value = 0.0
        }
    }

    fun updateProfit(newProfit: Double) {
        _totalProfit.value = newProfit
    }
}