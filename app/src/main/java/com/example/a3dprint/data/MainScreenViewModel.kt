package com.example.a3dprint.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainScreenViewModel : ViewModel() {
    private val _zakazky = MutableStateFlow(List(12) { "Zákazka č. ${it + 1}" })
    val zakazky: StateFlow<List<String>> = _zakazky
}