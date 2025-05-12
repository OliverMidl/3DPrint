package com.example.a3dprint.Screens

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3dprint.R
import com.example.a3dprint.navMenu.NavigationDestination
import com.example.a3dprint.viewModels.AddFilamentViewModel
import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import java.io.File
import java.io.FileOutputStream


/**
 * Destination pre navigáciu na obrazovku pridania filamentu.
 */
object AddFilamentScreenDest : NavigationDestination {
    override val route = "pridat_filament"
    override val titleRes = R.string.text_filamenty
}

/**
 * Zoznam vopred definovaných farieb filamentu.
 */
val predefinedColors = listOf(
    R.color.filament_red,
    R.color.filament_blue,
    R.color.filament_green,
    R.color.filament_yellow,
    R.color.filament_purple,
    R.color.filament_gold,
    R.color.filament_black,
    R.color.filament_white,
    R.color.filament_gray,
    R.color.filament_brown
)


/**
 * Obrazovka na pridanie nového filamentu.
 *
 * Umožňuje používateľovi zadať názov, popis, cenu, hmotnosť a farbu filamentu.
 * Podporuje aj výber fotografie z galérie alebo odfotenie pomocou fotoaparátu.
 *
 * @param onNavigateBack Callback funkcia, ktorá sa zavolá po úspešnom uložení alebo návrate späť.
 * @param viewModel ViewModel obrazovky.
 */
@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFilamentScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddFilamentViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    /**
     * Uloží fotografiu z URI do úložiska aplikácie.
     * Pri tvorbe tejto metódy som si pomohol s chatGBT ale rozumiem čo to robí.
     *
     * @param context Kontext aplikácie.
     * @param imageUri URI obrázka z fotoaparátu alebo galérie.
     * @return URI obrázka uloženého v úložisku.
     */
    fun saveImageToStorage(context: Context, imageUri: Uri): Uri {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(imageUri)
        val fileName = "filament_${System.currentTimeMillis()}.jpg"
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

    /**
     * Vytvorí URI pre novú fotografiu, ktorá bude uložená v úložisku zariadenia.
     *
     * @return URI, kde bude fotka uložená.
     */
    fun createImageUri(): Uri {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "photo_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
    }

    /**
     * Spustí kameru na nasnímanie fotografie, pričom sa vytvorí nové URI pre výstup.
     */
    fun launchCamera() {
        val uri = createImageUri()
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
            Toast.makeText(context, R.string.cameraAccess, Toast.LENGTH_SHORT).show()
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(x = (-22).dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(id = R.string.text_filamenty))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.KeyboardArrowDown,
                            contentDescription = stringResource(R.string.spat)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.blue3)
                )
            )
        },
        containerColor = colorResource(id = R.color.blue1)
    ) {innerPadding ->
        if (viewModel.showPhotoOptions.value) {
            AlertDialog(
                onDismissRequest = { viewModel.toggleShowPhotoOptions() },
                title = { Text(stringResource(R.string.moznost)) },
                text = { Text(stringResource(R.string.galeria_fotka)) },
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
                        Text(stringResource(R.string.odfotit))
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        viewModel.toggleShowPhotoOptions()
                        galleryLauncher.launch("image/*")
                    }) {
                        Text(stringResource(R.string.odkialG))
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(innerPadding)
                .background(colorResource(id = R.color.blue1))
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
                            contentDescription = stringResource(R.string.upload),
                            modifier = Modifier.size(48.dp)
                        )
                        Text(stringResource(R.string.pridatFoto))
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

            OutlinedTextField(
                value = uiState.name,
                onValueChange = viewModel::updateName,
                label = { Text(stringResource(R.string.nazov)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.description,
                onValueChange = viewModel::updateDescription,
                label = { Text(stringResource(R.string.popis)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.price,
                onValueChange = viewModel::updatePrice,
                label = { Text(stringResource(R.string.jednoCena)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.weight,
                onValueChange = viewModel::updateWeight,
                label = { Text(stringResource(R.string.gram)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Text(stringResource(R.string.vyber_fil))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .horizontalScroll(rememberScrollState())
            ) {
                predefinedColors.forEach { colorResId ->
                    val colorInt = ContextCompat.getColor(context, colorResId)
                    val colorHex = String.format("#%06X", 0xFFFFFF and colorInt)
                    val isSelected = uiState.selectedColor.toString() == colorHex

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(4.dp)
                            .background(
                                color = Color(colorInt),
                                shape = MaterialTheme.shapes.small
                            )
                            .border(
                                width = if (isSelected) 3.dp else 1.dp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                                shape = MaterialTheme.shapes.small
                            )
                            .clickable {

                                viewModel.updateSelectedColor(colorHex)
                            }
                    )
                }
            }

            Button(
                onClick = { viewModel.saveEntry(onNavigateBack) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = uiState.isValid
            ) {
                Text(stringResource(R.string.ulozit))
            }
        }
    }
}
