package com.example.a3dprint.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zakazky")
data class Zakazka(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val popis: String,
    val datum: String,
    val cena: Float,
    val photoUri: String? = null,
)