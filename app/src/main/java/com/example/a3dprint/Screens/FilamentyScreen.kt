package com.example.a3dprint.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.a3dprint.viewModels.FilamentViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3dprint.navMenu.NavigationDestination
import com.example.a3dprint.R
import androidx.core.graphics.toColorInt

/**
 * Objekt reprezentujúci cieľ navigácie pre obrazovku so zoznamom filamentov.
 */
object FilamentyScreenDest : NavigationDestination {
    override val route = "filamenty"
    override val titleRes = R.string.text_filamenty
}

/**
 * Zobrazí zoznam všetkých filamentov spolu s možnosťami pridania nového filamentu a prechodu na detail.
 *
 *
 * @param viewModel ViewModel, ktorý poskytuje zoznam filamentov
 * @param onNavigateToZakazky Callback pre navigáciu na obrazovku zákaziek
 * @param onNavigateToFinancie Callback pre navigáciu na obrazovku financií
 * @param onNavigateToAddFilament Callback pre navigáciu na obrazovku pridania filamentu
 * @param onNavigateToFilamentDetail Callback pre navigáciu na detail konkrétneho filamentu
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilamentyScreen(
    viewModel: FilamentViewModel = viewModel(),
    onNavigateToZakazky: () -> Unit,
    onNavigateToFinancie: () -> Unit,
    onNavigateToAddFilament: () -> Unit,
    onNavigateToFilamentDetail: (Int) -> Unit
) {
    val filaments = viewModel.filaments.collectAsState().value

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(id = R.color.blue3)
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = {onNavigateToZakazky() },
                    icon = { Icon(
                        Icons.Default.DateRange,
                        contentDescription = ZakazkyScreenDest.route,
                        tint = colorResource(id = R.color.dark_grey))
                    },
                    label = { Text(stringResource(ZakazkyScreenDest.titleRes)) },
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {  }, //sme tu
                    icon = { Icon(Icons.Default.Email, contentDescription = FilamentyScreenDest.route,
                        tint = colorResource(id = R.color.black)) },
                    label = { Text(stringResource(FilamentyScreenDest.titleRes)) },
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigateToFinancie() },
                    icon = { Icon(Icons.Default.Star, contentDescription = FilamentyScreenDest.route,
                        tint = colorResource(id = R.color.dark_grey)) },
                    label = { Text(stringResource(FinancieScreenDest.titleRes)) },

                    )


            }

        },
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(x = (-6).dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.text_filamenty))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.blue3)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToAddFilament() }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.pridat_filament))
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .background(colorResource(id = R.color.blue1))
                .fillMaxSize()
                .verticalScroll(rememberScrollState())

        ) {
            filaments.forEach { filament ->
                val color = try {
                    Color(filament.colorHex.toColorInt())
                } catch (e: IllegalArgumentException) {
                    Color.Gray
                }

                Card(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),

                    shape = RoundedCornerShape(13.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.blue2)),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(15.dp)
                                    .clip(CircleShape)
                                    .background(color)


                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Column(
                                modifier = Modifier
                                    .offset(x = 20.dp, y = 0.dp),

                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "${filament.name} (${filament.description})" , style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                )
                                Text(
                                    "${filament.currentWeight}g",
                                    style = MaterialTheme.typography.bodySmall,
                                    textAlign = TextAlign.Center,

                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = {
                                onNavigateToFilamentDetail(filament.id)
                            }) {
                                Icon(imageVector = Icons.Default.Settings, contentDescription = stringResource(R.string.editacia))
                            }
                        }
                    }
                }
            }
        }
    }
}
