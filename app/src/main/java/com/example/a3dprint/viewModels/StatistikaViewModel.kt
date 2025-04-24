package com.example.a3dprint.viewModels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.AppDatabase
import com.example.a3dprint.data.FilamentRepository
import com.example.a3dprint.data.ZakazkaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class StatistikaUiState(
    val pocetZakaziek: Int = 0,
    val celkovyZarobok: Double? = 0.0,
    val spotrebaFilamentu: Double = 0.0
)

class StatistikaViewModel(application: Application) : AndroidViewModel(application) {

    private val repositoryFilaments = FilamentRepository(
        AppDatabase.getDatabase(application).filamentDao()
    )
    val filamenty = repositoryFilaments.allFilaments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val repositoryZakazka = ZakazkaRepository(
        AppDatabase.getDatabase(application).zakazkaDao()
    )
    val zakazky = repositoryZakazka.allZakazka
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _statistika = MutableStateFlow(StatistikaUiState())
    val statistika: StateFlow<StatistikaUiState> = _statistika.asStateFlow()



    init {
        nacitajStatistiky()
    }

    private fun nacitajStatistiky() {
        viewModelScope.launch {
            combine(zakazky, filamenty) { zakazkyList, filamentyList ->
                val pocet = zakazkyList.size
                val zarobok = zakazkyList.sumOf { it.cena.toDouble() }
                val spotreba = filamentyList.sumOf { it.currentWeight.toDouble() }

                StatistikaUiState(
                    pocetZakaziek = pocet,
                    celkovyZarobok = zarobok,
                    spotrebaFilamentu = spotreba
                )
            }.collect { statistika ->
                _statistika.value = statistika
            }
        }
    }
}