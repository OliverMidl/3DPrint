package com.example.a3dprint.viewModels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.AppDatabase
import com.example.a3dprint.data.FilamentRepository
import com.example.a3dprint.data.ZakazkaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Data class, ktorá obsahuje stav štatistík pre zobrazenie v UI.
 *
 * @param pocetZakaziek Počet všetkých zakázok.
 * @param celkovyZarobok Celkový zárobok zo všetkých zakázok.
 * @param aktualneNaSkladeFilament Celková váha filamentov na sklade.
 * @param pocetFilament Počet všetkých filamentov.
 */
data class StatistikaUiState(
    val pocetZakaziek: Int = 0,
    val celkovyZarobok: Double? = 0.0,
    val aktualneNaSkladeFilament: Double = 0.0,
    val pocetFilament: Int = 0,
)

/**
 * ViewModel pre obrazovku štatistík, ktorá vypočítava a spravuje dáta o zakázkach a filamentoch.
 *
 * @param application Aplikácia, ktorá poskytuje kontext pre databázu.
 */
class StatistikaViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryFilaments = FilamentRepository(
        AppDatabase.getDatabase(application).filamentDao()
    )

    /**
     * Flow zoznamu všetkých filamentov.
     *
     * Zoznam je uložený v StateFlow, aby bolo možné dynamicky reagovať na zmeny.
     */

    val filamenty = repositoryFilaments.allFilaments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val repositoryZakazka = ZakazkaRepository(
        AppDatabase.getDatabase(application).zakazkaDao()
    )

    /**
     * Flow zoznamu všetkých zakázok.
     */
    val zakazky = repositoryZakazka.allZakazka
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * StateFlow, ktorý obsahuje aktuálny stav štatistík.
     */
    private val _statistika = MutableStateFlow(StatistikaUiState())
    val statistika: StateFlow<StatistikaUiState> = _statistika.asStateFlow()

    /**
     * Inicializuje ViewModel a spustí načítanie štatistík.
     */
    init {
        nacitajStatistiky()
    }

    private fun nacitajStatistiky() {
        viewModelScope.launch {
            combine(zakazky, filamenty) { zakazkyList, filamentyList ->
                val pocetZakazka = zakazkyList.size
                val zarobok = zakazkyList.sumOf { it.cena.toDouble() }
                val aktualneNaSkladeFilament = filamentyList.sumOf { it.aktualnaHmotnost.toDouble() }
                val pocetFilament = filamentyList.size

                StatistikaUiState(
                    pocetZakaziek = pocetZakazka,
                    celkovyZarobok = zarobok,
                    aktualneNaSkladeFilament = aktualneNaSkladeFilament,
                    pocetFilament = pocetFilament,
                )
            }.collect { statistika ->
                _statistika.value = statistika
            }
        }
    }
}