package com.example.a3dprint.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filaments")
data class Filament(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val price: Double,
    val maxWeight: Int,
    val currentWeight: Int = 0,
  //  val photoUri: String? = null // uložíme URI ako String
)