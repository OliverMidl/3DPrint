package com.example.a3dprint.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ZakazkyScreenViewModel : ViewModel() {
    private val _zakazky = MutableStateFlow(List(30) { "Zákazka č. ${it + 1}" })
    val zakazky: StateFlow<List<String>> = _zakazky
}