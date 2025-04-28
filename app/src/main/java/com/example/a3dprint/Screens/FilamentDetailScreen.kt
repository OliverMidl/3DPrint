package com.example.a3dprint.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.core.net.toUri



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

    viewModel: FilamentDetailViewModel = viewModel(),

) {

    val filament by viewModel.getFilamentById(filamentId).collectAsState(initial = null)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(x = (-22).dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center) {
                        Text("Filament")}
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Späť")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.blue3)
                )
            )
        },
        containerColor = colorResource(id = R.color.blue1)
    ) { innerPadding ->
        filament?.let { filamentData: Filament ->
            val color = try {
                Color(filamentData.colorHex.toColorInt())
            } catch (e: IllegalArgumentException) {
                Color.Gray
            }
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
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
                Box(
                    modifier = Modifier
                        .width(350.dp)
                        .height(8.dp)

                        .background(color)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Názov: ${filamentData.name}",
                    fontSize = 20.sp
                )
                Text(
                    text = "Popis: ${filamentData.description}",
                    fontSize = 20.sp
                )
                Text(
                    text = "Gramáž na sklade: ${filamentData.currentWeight}g",
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally),
                    modifier = Modifier.fillMaxWidth(),
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
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally),
                    modifier = Modifier.fillMaxWidth(),
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

                Spacer(modifier = Modifier.height(30.dp))

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