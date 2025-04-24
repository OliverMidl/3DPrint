package com.example.a3dprint.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.a3dprint.R
import com.example.a3dprint.data.Zakazka
import com.example.a3dprint.navMenu.NavigationDestination
import com.example.a3dprint.viewModels.ZakazkaDetailViewModel


object ZakazkaDetailScreenDest : NavigationDestination {
    override val route = "zakazka_detail"
    override val titleRes = R.string.text_zakazky

    const val zakazkaIdArg = "zakazkaId"
    val routeWithArgs = "$route/{$zakazkaIdArg}"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZakazkaDetailScreen(
    zakazkaId: Int,
    onBack: () -> Unit,
    viewModel: ZakazkaDetailViewModel = viewModel(),


    ) {
    val zakazka by viewModel.getZakazkaById(zakazkaId).collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail zakazky") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Späť")
                    }
                }
            )
        }
    ) { innerPadding ->
        zakazka?.let { zakazkaData: Zakazka ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    val imageUri = zakazkaData.photoUri?.toUri()

                    if (imageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUri),
                            contentDescription = "Zakazka Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp))
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Default Zakazka Image",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }


                Spacer(modifier = Modifier.height(8.dp))
                Text("Názov: ${zakazkaData.popis}")
                Text("Popis: ${zakazkaData.cena}")
                Text("Datum: ${zakazkaData.datum}")


                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        viewModel.deleteZakazka(zakazkaData.id)
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
