package com.example.a3dprint.data

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.a3dprint.R

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