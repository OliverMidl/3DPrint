package com.example.a3dprint.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Dátová trieda reprezentujúca jednu zákazku v databáze.
 *
 * @property id Primárny kľúč zákazky (automaticky generovaný).
 * @property popis Popis zákazky.
 * @property nazov Názov zákazky.
 * @property datum Dátum zákazky.
 * @property cena Cena zákazky.
 * @property typ Typ materiálu.
 * @property colorHex Farba zákazky.
 * @property photoUri URI obrázka priradeného k zákazke.
 */
@Entity(tableName = "zakazky")
data class Zakazka(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val popis: String,
    val nazov: String,
    val datum: String,
    val cena: Double,
    val typ: String,
    val colorHex: String,
    val photoUri: String? = null,
)