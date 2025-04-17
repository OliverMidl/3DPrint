package com.example.financie.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.Filament
import com.example.a3dprint.data.FilamentDatabase
import com.example.a3dprint.data.FilamentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddFilamentUiState(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val weight: String = ""
) {
    val isValid: Boolean
        get() = name.isNotBlank() && price.toDoubleOrNull() != null && weight.toIntOrNull() != null
}

class AddFilamentViewModel(application: Application) : AndroidViewModel(application) {

    private val database = FilamentDatabase.getDatabase(application)
    private val repository = FilamentRepository(database.filamentDao())
    val filaments = database.filamentDao()
        .getAllFilaments()
        .map { it.sortedBy { filament -> filament.name } } // napr. abecedne
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _uiState = MutableStateFlow(AddFilamentUiState())
    val uiState: StateFlow<AddFilamentUiState> = _uiState

    fun updateName(name: String) = _uiState.value.copy(name = name).also { _uiState.value = it }
    fun updateDescription(desc: String) = _uiState.value.copy(description = desc).also { _uiState.value = it }
    fun updatePrice(price: String) = _uiState.value.copy(price = price).also { _uiState.value = it }
    fun updateWeight(weight: String) = _uiState.value.copy(weight = weight).also { _uiState.value = it }
    //fun updatePhoto(uri: Uri) = _uiState.value.copy(photoUri = uri.toString()).also { _uiState.value = it }

    fun saveEntry(onSaved: () -> Unit) {
        val state = _uiState.value
        if (state.isValid) {
            viewModelScope.launch {
                repository.insert(
                    Filament(
                        name = state.name,
                        description = state.description,
                        price = state.price.toDouble(),
                        maxWeight = state.weight.toInt(),
                        //photoUri = state.photoUri
                    )
                )
                onSaved()
            }
        }
    }
}