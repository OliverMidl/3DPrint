package com.example.a3dprint.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(x = (-22).dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center) {
                        Text("Štatistika")}
                        },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Späť")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.blue3)
                )

            )
        },
        containerColor = colorResource(id = R.color.blue1)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .background(colorResource(id = R.color.blue1))
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard("Počet zákaziek", statistika.pocetZakaziek.toString())
            StatCard("Celkový zárobok", "%.2f €".format(statistika.celkovyZarobok))
            StatCard("Filament na sklade v g", "%.2f g".format(statistika.aktualneNaSkladeFilament))
            StatCard("Počet filamentov", statistika.pocetFilament.toString())
        }
    }
}


@Composable
fun StatCard(title: String, value: String) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.blue2)
        )
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, style = MaterialTheme.typography.headlineSmall)
        }
    }
}