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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.core.net.toUri


/**
 * Objekt reprezentujúci cieľ navigácie pre obrazovku s detailom filamentu.
 *
 */
object FilamentDetailScreenDest : NavigationDestination {
    override val route = "filament_detail"
    override val titleRes = R.string.text_filamenty

    const val routeWithArgs = "filament_detail/{filamentId}"
    const val filamentIdArg = "filamentId"
}

/**
 * Zobrazí detail vybraného filamentu vrátane fotografie, popisu, farby a aktuálnej hmotnosti.
 *
 * Používateľ môže zmeniť hmotnosť filamentu tlačidlami, alebo filament vymazať.
 *
 * @param filamentId ID filamentu, ktorého detail sa má zobraziť
 * @param onBack Callback, ktorý sa zavolá po stlačení tlačidla späť alebo po odstránení filamentu
 * @param viewModel ViewModel pre získanie a manipuláciu s údajmi o filamente
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilamentDetailScreen(
    filamentId: Int,
    onBack: () -> Unit,
    viewModel: FilamentDetailViewModel = viewModel(),
) {
    val context = LocalContext.current
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
                        Text(stringResource(R.string.filament))}
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = stringResource(R.string.spat))
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
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                ) {
                    val imageUri = filamentData.photoUri?.toUri()

                    if (imageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUri),
                            contentDescription = stringResource(R.string.obrazok_filament),
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp))
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = stringResource(R.string.default_obrazok),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .width(330.dp)
                        .height(8.dp)
                        .background(color)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = context.getString(R.string.nazov_, filamentData.name),
                    fontSize = 20.sp
                )
                Text(
                    text = context.getString(R.string.popis_, filamentData.popis),
                    fontSize = 20.sp
                )
                Text(
                    text = context.getString(R.string.gramaz_, filamentData.aktualnaHmotnost),
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(onClick = { viewModel.updateWeight(filamentData.id, 10) }) {
                        Text(stringResource(R.string.plus10))
                    }
                    Button(onClick = { viewModel.updateWeight(filamentData.id, 100) }) {
                        Text(stringResource(R.string.plus100))
                    }
                    Button(onClick = { viewModel.updateWeight(filamentData.id, 1000) }) {
                        Text(stringResource(R.string.plus1000))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(onClick = { viewModel.updateWeight(filamentData.id, -10) }) {
                        Text(stringResource(R.string.minus10))
                    }
                    Button(onClick = { viewModel.updateWeight(filamentData.id, -100) }) {
                        Text(stringResource(R.string.minus100))
                    }
                    Button(onClick = { viewModel.updateWeight(filamentData.id, -1000) }) {
                        Text(stringResource(R.string.minus1000))
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
                    Text(stringResource(R.string.vymazat))
                }
            }
        }
    }
}