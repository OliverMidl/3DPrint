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
/*
 <string name="route_zakazky">zakazky</string>
    <string name="route_filamenty">filamenty</string>
    <string name="route_financie">financie</string>
 */