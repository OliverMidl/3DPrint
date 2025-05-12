package com.example.a3dprint.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tabuľka databázy predstavujúca jeden filament.
 *
 * Obsahuje základné informácie o jednom type filamentu vrátane
 * názvu, popisu, ceny, maximálnej a aktuálnej váhy, voliteľnej fotky a farby.
 *
 * @property id Primárny kľúč. Automaticky generovaný.
 * @property name Názov filamentu.
 * @property description Stručný popis.
 * @property price Cena filamentu v eurách.
 * @property maxWeight Pôvodná maximálna hmotnosť.
 * @property currentWeight Aktuálna zostávajúca hmotnosť.
 * @property photoUri URI adresa k fotografii filamentu.
 * @property colorHex Farba filamentu.
 */
@Entity(tableName = "filaments")
data class Filament(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val price: Double,
    val maxWeight: Int,
    val currentWeight: Int = maxWeight,
    val photoUri: String? = null,
    val colorHex: String = "#FFFFFFFF"
)