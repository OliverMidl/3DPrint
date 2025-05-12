package com.example.a3dprint.viewModels

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3dprint.data.AppDatabase
import com.example.a3dprint.data.Zakazka
import com.example.a3dprint.data.ZakazkaRepository
import com.example.a3dprint.notification.scheduleNotification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date


/**
 * Trieda reprezentujúca stav UI pre pridanie zákazky.
 *
 * @property popis Popis zákazky.
 * @property datum Dátum zákazky vo formáte "dd.MM.yyyy".
 * @property cena Cena zákazky ako reťazec.
 * @property nazov Názov zákazky.
 * @property photoUri URI obrázka zákazky (voliteľné).
 * @property selectedColor Hex kód vybratej farby (voliteľné).
 * @property typ Typ zákazky.
 */
data class AddZakazkaUiState(
    val popis: String = "",
    val datum: String = "",
    val cena: String = "",
    val nazov: String = "",
    val photoUri: String? = null,
    val selectedColor: String? = null,
    val typ: String = ""
) {
    val isValid: Boolean
        get() = popis.isNotBlank() && datum.isNotBlank() && cena.toDoubleOrNull() != null
}

/**
 * ViewModel pre obrazovku pridania zákazky.
 *
 * Zodpovedá za správu vstupných údajov, ukladanie do databázy
 * Inicializuje ViewModel s databázou.
 */
class AddZakazkaViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = ZakazkaRepository(database.zakazkaDao())

    /**
     * Zobrazenie možností výberu fotografie (kamera/galéria).
     */
    var showPhotoOptions = mutableStateOf(false)
        private set

    /**
     * Stav povolenia pre kameru.
     */
    var cameraPermissionGranted = mutableStateOf(false)
        private set


    /**
     * URI fotografie vytvorenej kamerou.
     */
    var photoUri = mutableStateOf<Uri?>(null)
        private set

    /**
     * Aktualizuje stav povolenia pre kameru.
     *
     * @param isGranted `true` ak bolo udelené povolenie.
     */
    fun updateCameraPermissionGranted(isGranted: Boolean) {
        cameraPermissionGranted.value = isGranted
    }

    /**
     * Prepína zobrazenie možností pre fotografiu.
     */
    fun toggleShowPhotoOptions() {
        showPhotoOptions.value = !showPhotoOptions.value
    }

    val selectedDateMillis = mutableStateOf<Long?>(null)

    /**
     * Aktualizuje dátum zákazky podľa timestampu a nastaví ho vo formáte "dd.MM.yyyy".
     *
     * @param millis Čas v milisekundách.
     */
    @SuppressLint("SimpleDateFormat")
    fun updateSelectedDate(millis: Long) {
        selectedDateMillis.value = millis
        val formatted = SimpleDateFormat("dd.MM.yyyy").format(Date(millis))
        updateDatum(formatted)
    }

    val openDateDialog = mutableStateOf(false)

    /**
     * Otvorí alebo zatvorí dialóg výberu dátumu.
     *
     * @param open true, ak sa má dialóg zobraziť.
     */
    fun toggleDateDialog(open: Boolean) {
        openDateDialog.value = open
    }

    private val _uiState = MutableStateFlow(AddZakazkaUiState())
    val uiState: StateFlow<AddZakazkaUiState> = _uiState

    /**
     * Aktualizuje popis zákazky.
     */
    fun updatePopis(title: String) = _uiState.value.copy(popis = title).also { _uiState.value = it }

    /**
     * Aktualizuje typ zákazky.
     */
    fun updateTyp(type: String) = _uiState.value.copy(typ = type).also { _uiState.value = it }

    /**
     * Aktualizuje názov zákazky.
     */
    fun updateNazov(name: String) = _uiState.value.copy(nazov = name).also { _uiState.value = it }

    /**
     * Aktualizuje dátum zákazky.
     */
    fun updateDatum(customer: String) = _uiState.value.copy(datum = customer).also { _uiState.value = it }

    /**
     * Aktualizuje cenu zákazky.
     */
    fun updateCena(price: String) = _uiState.value.copy(cena = price).also { _uiState.value = it }

    /**
     * Aktualizuje URI fotografie zákazky.
     */
    fun updatePhotoUri(uri: String) {
        _uiState.value = _uiState.value.copy(photoUri = uri)
    }

    /**
     * Aktualizuje vybranú farbu zákazky.
     */
    fun updateSelectedColor(colorResId: String) {
        _uiState.value = _uiState.value.copy(selectedColor = colorResId)
    }

    val checkAndRequestNotificationPermissionRequired = mutableStateOf(false)

    /**
     * Kontroluje a požaduje povolenie na zobrazovanie notifikácií.
     */
    fun checkAndRequestNotificationPermission() {
        val notificationManager = NotificationManagerCompat.from(getApplication<Application>())
        if (!notificationManager.areNotificationsEnabled()) {
            checkAndRequestNotificationPermissionRequired.value = true
        }
    }

    /**
     * Uloží zákazku do databázy a naplánuje notifikáciu.
     */
    fun saveEntry(onSaved: () -> Unit) {
        val state = _uiState.value
        if (state.isValid) {
            viewModelScope.launch {
                repository.insert(
                    Zakazka(
                        popis = state.popis,
                        datum = state.datum,
                        cena = state.cena.toDouble(),
                        photoUri = state.photoUri?.toString(),
                        nazov = state.nazov,
                        colorHex = state.selectedColor.toString(),
                        typ = state.typ,
                    )
                )
                val context = getApplication<Application>().applicationContext
                checkAndRequestNotificationPermission()
                scheduleNotification(context, state.popis, state.datum)
                onSaved()
            }
        }
    }
}
