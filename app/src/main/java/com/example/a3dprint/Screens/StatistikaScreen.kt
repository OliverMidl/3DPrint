package com.example.a3dprint.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3dprint.R
import com.example.a3dprint.navMenu.NavigationDestination
import com.example.a3dprint.viewModels.StatistikaViewModel

object StatistikaScreenDest : NavigationDestination {
    override val route = "statistika"
    override val titleRes = R.string.text_statistika
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatistikaScreen(
    viewModel: StatistikaViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val statistika = viewModel.statistika.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Štatistika") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Späť")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard("Počet zákaziek", statistika.pocetZakaziek.toString())
            StatCard("Celkový zárobok", "%.2f €".format(statistika.celkovyZarobok))
            StatCard("Spotrebovaný filament", "%.2f g".format(statistika.spotrebaFilamentu))
        }
    }
}


@Composable
fun StatCard(title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, style = MaterialTheme.typography.headlineSmall)
        }
    }
}