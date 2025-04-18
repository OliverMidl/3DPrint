package com.example.a3dprint.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a3dprint.viewModels.FinancieScreenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3dprint.navMenu.NavigationDestination
import com.example.a3dprint.R
import androidx.compose.runtime.getValue

object FinancieScreenDest : NavigationDestination {
    override val route = "financie"
    override val titleRes = R.string.text_financie
}

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancieScreen(
    viewModel: FinancieScreenViewModel = viewModel(),
    //onStatisticsClick: () -> Unit,
    onNavigateToZakazky: () -> Unit,
    onNavigateToFilamenty: () -> Unit,
) {
    val totalProfit by viewModel.totalProfit.collectAsState()

    Scaffold(
        containerColor = colorResource(id = R.color.blue1),
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
                    selected = false,
                    onClick = { onNavigateToFilamenty()  },
                    icon = { Icon(Icons.Default.Email, contentDescription = FilamentyScreenDest.route,
                        tint = colorResource(id = R.color.dark_grey)) },
                    label = { Text(stringResource(FilamentyScreenDest.titleRes)) },
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {  },// sme tu
                    icon = { Icon(Icons.Default.Star, contentDescription = FinancieScreenDest.route,
                        tint = colorResource(id = R.color.black)) },
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
                            .clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.text_financie))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.blue3)
                )
            )
        },

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 32.dp)
                .background(colorResource(id = R.color.blue1)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Celkový profit", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = String.format("%.2f €", totalProfit),
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {/*onStatisticsClick*/},
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(48.dp)
            ) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Štatistiky", color = Color.Black)
            }
        }
    }
}
