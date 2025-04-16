package com.example.financie.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class AddFilamentUiState(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val weight: String = ""
) {
    val isValid: Boolean
        get() = name.isNotBlank() && price.toDoubleOrNull() != null && weight.toIntOrNull() != null
}

class AddFilamentViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AddFilamentUiState())
    val uiState: StateFlow<AddFilamentUiState> = _uiState

    fun updateName(newName: String) {
        _uiState.update { it.copy(name = newName) }
    }

    fun updateDescription(newDescription: String) {
        _uiState.update { it.copy(description = newDescription) }
    }

    fun updatePrice(newPrice: String) {
        _uiState.update { it.copy(price = newPrice) }
    }

    fun updateWeight(newWeight: String) {
        _uiState.update { it.copy(weight = newWeight) }
    }

    fun saveEntry(onSaved: () -> Unit) {
        // TODO: Uložiť do databázy
        println("Saving: ${_uiState.value}")
        onSaved()
    }
}