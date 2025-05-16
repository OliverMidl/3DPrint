package com.example.a3dprint.viewModels

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.Filament
import com.example.a3dprint.data.AppDatabase
import com.example.a3dprint.data.FilamentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Trieda reprezentujúca stav UI pre pridávanie nového filamentu.
 *
 * @property nazov Názov filamentu
 * @property popis Popis filamentu
 * @property cena Cena filamentu ako text
 * @property hmotnost Váha filamentu ako text
 * @property photoUri URI obrázka filamentu
 * @property farbaFilamentu Vybraná farba filamentu v hex formáte
 */
data class AddFilamentUiState(
    val nazov: String = "",
    val popis: String = "",
    val cena: String = "",
    val hmotnost: String = "",
    val photoUri: String? = null,
    val farbaFilamentu: String? = null
) {
    val isValid: Boolean
        get() = nazov.isNotBlank() && cena.toDoubleOrNull() != null && hmotnost.toIntOrNull() != null && !photoUri.isNullOrEmpty()
}

/**
 * ViewModel pre obrazovku pridávania nového filamentu.
 *
 * Ukladá a spravuje stav používateľského rozhrania a zabezpečuje ukladanie dát do databázy.
 * Inicializuje ViewModel s prístupom k databáze cez Application.
 */
class AddFilamentViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = FilamentRepository(database.filamentDao())
    private val _uiState = MutableStateFlow(AddFilamentUiState())
    val uiState: StateFlow<AddFilamentUiState> = _uiState

    /**
     * Zobrazenie výberu fotky (galéria/kamera).
     */
    var showPhotoOptions = mutableStateOf(false)
        private set

    /**
     * Indikátor, či bolo udelené povolenie ku kamere.
     */
    var cameraPermissionGranted = mutableStateOf(false)
        private set

    /**
     * Aktuálny URI objekt z kamery.
     */
    var photoUri = mutableStateOf<Uri?>(null)
        private set

    /**
     * Aktualizuje stav povolenia kamery.
     *
     * @param isGranted true, ak má aplikácia prístup ku kamere.
     */
    fun updateCameraPermissionGranted(isGranted: Boolean) {
        cameraPermissionGranted.value = isGranted
    }

    /**
     * Prepína zobrazenie výberu fotky.
     */
    fun toggleShowPhotoOptions() {
        showPhotoOptions.value = !showPhotoOptions.value
    }

    /**
     * Aktualizuje vybranú farbu.
     *
     * @param colorResId Farba vo formáte hex.
     */
    fun updateSelectedColor(colorResId: String) {
        _uiState.value = _uiState.value.copy(farbaFilamentu = colorResId)
    }

    /**
     * Aktualizuje názov filamentu.
     */
    fun updateName(name: String) = _uiState.value.copy(nazov = name).also { _uiState.value = it }

    /**
     * Aktualizuje popis filamentu.
     */
    fun updateDescription(desc: String) = _uiState.value.copy(popis = desc).also { _uiState.value = it }

    /**
     * Aktualizuje cenu filamentu.
     */
    fun updatePrice(price: String) = _uiState.value.copy(cena = price).also { _uiState.value = it }

    /**
     * Aktualizuje hmotnosť filamentu.
     */
    fun updateWeight(weight: String) = _uiState.value.copy(hmotnost = weight).also { _uiState.value = it }

    /**
     * Nastaví URI obrázka.
     *
     * @param uri Reťazec reprezentujúci URI obrázka.
     */
    fun updatePhotoUri(uri: String) {
        _uiState.value = _uiState.value.copy(photoUri = uri)
    }




    /**
     * Uloží nový záznam filamentu do databázy, ak sú údaje platné.
     *
     * @param onSaved Callback po úspešnom uložení.
     */
    fun saveEntry(onSaved: () -> Unit) {
        val state = _uiState.value
        if (state.isValid) {
            viewModelScope.launch {
                repository.insert(
                    Filament(
                        name = state.nazov,
                        popis = state.popis,
                        cena = state.cena.toDouble(),
                        hmotnost = state.hmotnost.toInt(),
                        photoUri = state.photoUri?.toString(),
                        colorHex = state.farbaFilamentu.toString()
                    ) ,
                )
                onSaved()
            }
        }

    }
}