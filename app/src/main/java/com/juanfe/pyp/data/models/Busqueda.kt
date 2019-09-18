package com.juanfe.pyp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "busqueda")
data class Busqueda(
    var date: Long,
    var plate: String,
    var contravention: Boolean
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    constructor(): this(0,"",false)
}