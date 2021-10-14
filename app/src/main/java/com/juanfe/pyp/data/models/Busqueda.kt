package com.juanfe.pyp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "busqueda")
data class Busqueda(
    @PrimaryKey var plate: String,
    var descripcion: String
){
    constructor(): this("", "")
}