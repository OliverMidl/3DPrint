package com.example.a3dprint.viewModels

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.AppDatabase
import com.example.a3dprint.data.Zakazka
import com.example.a3dprint.data.ZakazkaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class AddZakazkaUiState(
    val popis: String = "",
    val datum: String = "",
    val cena: String = "",
    val photoUri: String? = null,
) {
    val isValid: Boolean
        get() = popis.isNotBlank() && datum.isNotBlank() && cena.toDoubleOrNull() != null
}

class AddZakazkaViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = ZakazkaRepository(database.zakazkaDao())

    var showPhotoOptions = mutableStateOf(false)
        private set

    var cameraPermissionGranted = mutableStateOf(false)
        private set

    var photoUri = mutableStateOf<Uri?>(null)
        private set

    fun updateCameraPermissionGranted(isGranted: Boolean) {
        cameraPermissionGranted.value = isGranted
    }

    fun toggleShowPhotoOptions() {
        showPhotoOptions.value = !showPhotoOptions.value
    }

    private val _uiState = MutableStateFlow(AddZakazkaUiState())
    val uiState: StateFlow<AddZakazkaUiState> = _uiState

    fun updatePopis(title: String) = _uiState.value.copy(popis = title).also { _uiState.value = it }
    fun updateDatum(customer: String) = _uiState.value.copy(datum = customer).also { _uiState.value = it }
    fun updateCena(price: String) = _uiState.value.copy(cena = price).also { _uiState.value = it }
    fun updatePhotoUri(uri: String) {
        _uiState.value = _uiState.value.copy(photoUri = uri)
    }
    fun saveEntry(onSaved: () -> Unit) {
        val state = _uiState.value
        if (state.isValid) {
            viewModelScope.launch {
                repository.insert(
                    Zakazka(
                        popis = state.popis,
                        datum = state.datum,
                        cena = state.cena.toFloat(),
                        photoUri = state.photoUri?.toString(),
                    )
                )
                onSaved()
            }
        }
    }
}
