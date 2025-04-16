package com.example.a3dprint.viewModels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Filament(
    val name: String,
    val currentWeight: Int,
    val maxWeight: Int,
    val color: Color // toto je DÔLEŽITÉ
)

class FilamentViewModel : ViewModel() {
    private val _filaments = MutableStateFlow(
        listOf(
            Filament("PLA - Light Blue Gembird", 750, 1000, Color.Blue),
            Filament("PLA - Red Creality", 500, 1000, Color.Red)
        )
    )
    val filaments: StateFlow<List<Filament>> = _filaments
}