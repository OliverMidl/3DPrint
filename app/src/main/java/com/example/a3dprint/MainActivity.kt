package com.example.a3dprint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.a3dprint.navMenu.MainNavigation
import com.example.a3dprint.ui.theme._3DPrintTheme

/**
 * Hlavná aktivita aplikácie, ktorá nastavuje obsah obrazovky a definuje navigáciu.
 *
 * Táto aktivita používa Jetpack Compose na nastavenie UI. Obsah je zobrazený s
 * navigáciou, ktorá je riadená cez MainNavigation.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _3DPrintTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = R.color.blue1))
                ) {
                    MainNavigation()
                }
            }
        }
    }
}
