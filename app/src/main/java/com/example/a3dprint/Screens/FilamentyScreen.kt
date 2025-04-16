package com.example.a3dprint.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.a3dprint.viewModels.FilamentViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3dprint.navMenu.NavigationDestination
import com.example.a3dprint.R

object FilamentyScreenDest : NavigationDestination {
    override val route = "filamenty"
    override val titleRes = R.string.text_filamenty
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilamentyScreen(
    viewModel: FilamentViewModel = viewModel(),
    onNavigateToZakazky: () -> Unit,
    onNavigateToFinancie: () -> Unit,
    onNavigateToAddFilament: () -> Unit // <- PRIDANÉ
) {
    val filaments = viewModel.filaments.collectAsState().value
    var selectedTab by remember { mutableStateOf(1) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(id = R.color.blue3) // Farba pozadia celého bottomBar
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
                    onClick = {  }, // sme na Zákazkách
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
                            .clip(RoundedCornerShape(16.dp)), // Zaoblené rohy
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.text_filamenty))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.blue3) // Tu môžeš nastaviť svoju požadovanú farbu pozadia
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToAddFilament() }) {
                Icon(Icons.Default.Add, contentDescription = "Add Filament")
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .background(colorResource(id = R.color.blue1))
                .fillMaxSize()

        ) {
            filaments.forEach { filament ->
                Card(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),

                    shape = RoundedCornerShape(13.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.blue2)),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape) // najprv orežeme tvar
                                    .background(filament.color) // potom dáme farbu
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(filament.name, style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    "${filament.currentWeight}g / ${filament.maxWeight}g",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                        IconButton(onClick = { /* nastavenia */ }) {
                            Icon(Icons.Default.Settings,
                                contentDescription = "Nastavenia",
                                )
                        }
                    }
                }
            }
        }
    }
}
