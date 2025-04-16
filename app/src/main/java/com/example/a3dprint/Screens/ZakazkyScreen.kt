package com.example.a3dprint.Screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.a3dprint.viewModels.ZakazkyScreenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a3dprint.navMenu.NavigationDestination
import com.example.a3dprint.R

object ZakazkyScreenDest : NavigationDestination {
    override val route = "zakazky"
    override val titleRes = R.string.text_zakazky
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZakazkyScreen(
    modifier: Modifier = Modifier,
    viewModel: ZakazkyScreenViewModel = viewModel(),
    onNavigateToFilamenty: () -> Unit,
    onNavigateToFinancie: () -> Unit,

) {
    val zakazky = viewModel.zakazky.collectAsState().value
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(id = R.color.blue3) // Farba pozadia celého bottomBar
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = {}, // sme na Zákazkách
                    icon = { Icon(
                        Icons.Default.DateRange,
                        contentDescription = ZakazkyScreenDest.route,
                        tint = colorResource(id = R.color.black))
                           },
                    label = { Text(stringResource(ZakazkyScreenDest.titleRes)) },
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigateToFilamenty() },
                    icon = { Icon(Icons.Default.Email, contentDescription = FilamentyScreenDest.route,
                        tint = colorResource(id = R.color.dark_grey)) },
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
                        Text(stringResource(R.string.text_zakazky))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.blue3) // Tu môžeš nastaviť svoju požadovanú farbu pozadia
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.text_AddEdit))
            }
        },
        content = { padding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(colorResource(id = R.color.blue1)), // tu nastavíš svoju farbu

                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),

            ) {
                items(zakazky.size) { index ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)) // Zaoblené rohy
                            .background(colorResource(id = R.color.blue2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(zakazky[index])
                    }
                }
            }
        }
    )
}
