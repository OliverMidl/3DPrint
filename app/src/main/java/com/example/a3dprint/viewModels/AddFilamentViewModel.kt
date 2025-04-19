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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.core.net.toUri

data class AddFilamentUiState(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val weight: String = "",
    val photoUri: String? = null
) {
    val isValid: Boolean
        get() = name.isNotBlank() && price.toDoubleOrNull() != null && weight.toIntOrNull() != null && !photoUri.isNullOrEmpty()
}

class AddFilamentViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = FilamentRepository(database.filamentDao())
    private val _uiState = MutableStateFlow(AddFilamentUiState())
    val uiState: StateFlow<AddFilamentUiState> = _uiState

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


    fun updateName(name: String) = _uiState.value.copy(name = name).also { _uiState.value = it }
    fun updateDescription(desc: String) = _uiState.value.copy(description = desc).also { _uiState.value = it }
    fun updatePrice(price: String) = _uiState.value.copy(price = price).also { _uiState.value = it }
    fun updateWeight(weight: String) = _uiState.value.copy(weight = weight).also { _uiState.value = it }

    fun updatePhotoUri(uri: String) {
        _uiState.value = _uiState.value.copy(photoUri = uri)
    }




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
                        photoUri = state.photoUri?.toString()
                    )
                )
                onSaved()
            }
        }
    }
}