package com.example.a3dprint.Screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.a3dprint.R
import com.example.a3dprint.data.Filament
import com.example.a3dprint.navMenu.NavigationDestination
import com.example.a3dprint.viewModels.FilamentDetailViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.a3dprint.data.AppDatabase
import com.example.a3dprint.viewModels.FilamentDetailViewModelFactory
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.example.a3dprint.viewModels.AddFilamentViewModel


object FilamentDetailScreenDest : NavigationDestination {
    override val route = "filament_detail"
    override val titleRes = R.string.text_filamenty

    const val routeWithArgs = "filament_detail/{filamentId}"
    const val filamentIdArg = "filamentId"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilamentDetailScreen(
    filamentId: Int,
    onBack: () -> Unit,
    navBackStackEntry: NavBackStackEntry,
    //viewModel: FilamentDetailViewModel = viewModel()
) {

    val context = LocalContext.current
    val filamentDao = AppDatabase.getDatabase(context).filamentDao()

    val savedStateHandle: SavedStateHandle = navBackStackEntry.savedStateHandle


    val viewModel: FilamentDetailViewModel = viewModel(
        factory = FilamentDetailViewModelFactory(filamentDao, savedStateHandle)
    )
    val filament by viewModel.getFilamentById(filamentId).collectAsState(initial = null)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail filamentu") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Späť")
                    }
                }
            )
        }
    ) { innerPadding ->
        filament?.let { filamentData: Filament ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    val imageUri = filamentData.photoUri?.toUri()

                    if (imageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUri),
                            contentDescription = "Filament Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp))
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Default Filament Image",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }


                Spacer(modifier = Modifier.height(8.dp))
                Text("Názov: ${filamentData.name}")
                Text("Popis: ${filamentData.description}")

                Text("Gramáž na sklade: ${filamentData.currentWeight}g")


                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { viewModel.updateWeight(filamentData.id, 10) }) {
                        Text("+10g")
                    }
                    Button(onClick = { viewModel.updateWeight(filamentData.id, 100) }) {
                        Text("+100g")
                    }
                    Button(onClick = { viewModel.updateWeight(filamentData.id, 1000) }) {
                        Text("+1000g")
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { viewModel.updateWeight(filamentData.id, -10) }) {
                        Text("-10g")
                    }
                    Button(onClick = { viewModel.updateWeight(filamentData.id, -100) }) {
                        Text("-100g")
                    }
                    Button(onClick = { viewModel.updateWeight(filamentData.id, -1000) }) {
                        Text("-1000g")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        viewModel.deleteFilament(filamentData.id)
                        onBack()
                              },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Vymazať")
                }
            }
        }
    }
}