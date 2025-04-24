package com.example.a3dprint.Screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.a3dprint.R
import com.example.a3dprint.navMenu.NavigationDestination
import com.example.a3dprint.viewModels.AddZakazkaViewModel
import java.io.File
import java.io.FileOutputStream

object AddZakazkaScreenDest : NavigationDestination {
    override val route = "pridat_zakazku"
    override val titleRes = R.string.text_zakazky
}
@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddZakazkaScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddZakazkaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    fun saveImageToStorage(context: Context, imageUri: Uri): Uri {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(imageUri)
        val fileName = "zakazka_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)
        val outputStream = FileOutputStream(file)

        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()

        return file.toUri()
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            viewModel.photoUri.value?.let { uri ->
                val savedUri = saveImageToStorage(context, uri)
                viewModel.updatePhotoUri(savedUri.toString())
            }
        }
    }

    fun createImageUri(context: Context): Uri {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "photo_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
    }

    fun launchCamera() {
        val uri = createImageUri(context)
        viewModel.photoUri.value = uri
        cameraLauncher.launch(uri)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.updateCameraPermissionGranted(isGranted)
        if (isGranted) {
            launchCamera()
        } else {
            Toast.makeText(context, "Povolenie na kameru je potrebné", Toast.LENGTH_SHORT).show()
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val savedUri = saveImageToStorage(context, it)
            viewModel.updatePhotoUri(savedUri.toString())
        }
    }

    val openDateDialog by viewModel.openDateDialog
    val datePickerState = rememberDatePickerState()


    if (openDateDialog) {
        DatePickerDialog(
            onDismissRequest = { viewModel.toggleDateDialog(false) },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { viewModel.updateSelectedDate(it) }
                    viewModel.toggleDateDialog(false)
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.toggleDateDialog(false) }) {
                    Text("Zrušiť")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(id = R.string.text_zakazky))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Späť")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (viewModel.showPhotoOptions.value) {
            AlertDialog(
                onDismissRequest = { viewModel.toggleShowPhotoOptions() },
                title = { Text("Vybrať možnosť") },
                text = { Text("Chceš vybrať fotku z galérie alebo odfotiť?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.toggleShowPhotoOptions()
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED
                        ) {
                            launchCamera()
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }) {
                        Text("Odfotiť")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        viewModel.toggleShowPhotoOptions()
                        galleryLauncher.launch("image/*")
                    }) {
                        Text("Z galérie")
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    viewModel.toggleShowPhotoOptions()
                },
                modifier = Modifier.fillMaxWidth().height(150.dp)
            ) {
                if (uiState.photoUri == null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(R.drawable.ic_launcher_background),
                            contentDescription = "Upload",
                            modifier = Modifier.size(48.dp)
                        )
                        Text("Pridať fotku")
                    }
                }
                uiState.photoUri?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(vertical = 8.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            ) {
                OutlinedTextField(
                    value = uiState.datum,
                    onValueChange = {},
                    label = { Text("Dátum") },
                    readOnly = true,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { viewModel.toggleDateDialog(true) }
                        .background(Color.Transparent)
                )
            }
            OutlinedTextField(
                value = uiState.popis,
                onValueChange = viewModel::updatePopis,
                label = { Text("Názov zákazky") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.cena,
                onValueChange = viewModel::updateCena,
                label = { Text("Cena") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )


            Button(
                onClick = { viewModel.saveEntry(onNavigateBack) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = uiState.isValid
            ) {
                Text("Uložiť zákazku")
            }
        }
    }
}

