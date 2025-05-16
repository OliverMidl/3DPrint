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
 * @property popis Stručný popis.
 * @property cena Cena filamentu v eurách.
 * @property hmotnost Pôvodná maximálna hmotnosť.
 * @property aktualnaHmotnost Aktuálna zostávajúca hmotnosť.
 * @property photoUri URI adresa k fotografii filamentu.
 * @property colorHex Farba filamentu.
 */
@Entity(tableName = "filaments")
data class Filament(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val popis: String,
    val cena: Double,
    val hmotnost: Int,
    val aktualnaHmotnost: Int = hmotnost,
    val photoUri: String? = null,
    val colorHex: String = "#FFFFFFFF"
)