package com.example.a3dprint.data

import androidx.room.Entity
import androidx.room.PrimaryKey

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