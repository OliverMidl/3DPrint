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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.a3dprint.viewModels.FilamentViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancieScreen(
    viewModel: FilamentViewModel = viewModel(),
    onNavigateToZakazky: () -> Unit,
    onNavigateToFilamenty: () -> Unit
) {
    val filaments = viewModel.filaments.collectAsState().value
    var selectedTab by remember { mutableStateOf(1) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigateToZakazky() },
                    icon = { Icon(Icons.Default.Email, contentDescription = "Zákazky") },
                    label = { Text("Zákazky") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {onNavigateToFilamenty()},
                    icon = { Icon(Icons.Default.Email, contentDescription = "Filamenty") },
                    label = { Text("Filamenty") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {}, // sme tu
                    icon = { Icon(Icons.Default.Star, contentDescription = "Financie") },
                    label = { Text("Financie") }
                )
                // ...
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Filamenty") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Pridaj nový filament */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add Filament")
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            filaments.forEach { filament ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
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
                            Icon(Icons.Default.Settings, contentDescription = "Nastavenia")
                        }
                    }
                }
            }
        }
    }
}
