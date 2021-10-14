package com.juanfe.pyp.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.juanfe.pyp.data.models.Busqueda

@Dao
interface BusquedaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(busquedas: List<Busqueda>)

    @Query("SELECT * FROM busqueda")
    fun getBusquedas(): LiveData<MutableList<Busqueda>>

    @Query("SELECT * FROM busqueda WHERE plate = :plate")
    fun getBusquedasByPlate(plate: String): MutableList<Busqueda>

    @Query("SELECT * FROM busqueda WHERE descripcion LIKE '%' || :description || '%'")
    fun getBusquedasByDescription(description: String): MutableList<Busqueda>

    @Query("DELETE FROM busqueda WHERE plate = :plate")
    fun deleteBusquedaByPlate(plate: String): Int
}