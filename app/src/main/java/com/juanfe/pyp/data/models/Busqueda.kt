package com.juanfe.pyp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "busqueda")
data class Busqueda(
    var plate: String,
    var descripcion: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    constructor(): this("", "")
}