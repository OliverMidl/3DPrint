package com.example.a3dprint.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import com.example.a3dprint.viewModels.ZakazkyScreenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
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
    onNavigateToAddZakazka: () -> Unit,
    onNavigateToDetailZakazka: (Long) -> Unit

) {
    val zakazky = viewModel.zakazky.collectAsState().value
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(id = R.color.blue3)
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
                            .offset(x = (-6).dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.text_zakazky))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.blue3)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {onNavigateToAddZakazka()}) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.text_AddEdit))
            }
        },
        content = { padding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(colorResource(id = R.color.blue1)),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(zakazky.size) { index ->
                    val zakazka = zakazky[index]
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(colorResource(id = R.color.blue2))
                            .clickable { onNavigateToDetailZakazka(zakazka.id.toLong()) },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = zakazka.popis,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )

                            val imageUri = zakazka.photoUri?.toUri()
                            if (imageUri != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(imageUri),
                                    contentDescription = "Zakazka Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                    contentDescription = "Default Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}