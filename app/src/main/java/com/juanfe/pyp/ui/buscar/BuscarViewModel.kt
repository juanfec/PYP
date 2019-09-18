package com.juanfe.pyp.ui.buscar

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.einvopos.e_invopos.utils.Coroutines
import com.juanfe.pyp.R
import com.juanfe.pyp.data.AppDatabase
import com.juanfe.pyp.data.models.Busqueda
import com.juanfe.pyp.utlis.Constants
import com.juanfe.pyp.utlis.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Date
import java.time.Instant
import java.util.*

class BuscarViewModel(var database: AppDatabase) : ViewModel() {
    var buscarListener: BuscarListener? = null
    var plate: String? = null
    val regexPlate = Regex("[a-zA-Z]{3}[0-9]{3}")

    val busquedas by lazyDeferred {
        getBusquedas()
    }

    suspend fun getBusquedas(): LiveData<MutableList<Busqueda>> {
        return withContext(Dispatchers.IO){
            database.getBusquedaDao().getBusquedas()
        }
    }

    suspend fun saveBusquedas(list: List<Busqueda>){
        database.getBusquedaDao().insertOrUpdate(list)
    }

    fun getBusquedasByPlate(plate: String): MutableList<Busqueda> {
        return database.getBusquedaDao().getBusquedasByPlate(plate)
    }

    /**
     * we check if the plate is right and if exists a contravention then we save the search for the log
     */
    fun buscar(view: View){
        Log.e("viewmodel","entre")
        if (plate.isNullOrEmpty() ){
            buscarListener!!.onError(R.string.campos_vacios)
        }else if(!plate!!.matches(regexPlate)){
            buscarListener!!.onError(R.string.campos_incorrectos)
        }else{
            var last = plate!!.takeLast(1)
            var contravention = false
            if(Constants.PICOYPLACA[Calendar.getInstance().time.day].contains(last.toInt())){
                contravention = true
            }
            var busqueda = Busqueda(Calendar.getInstance().timeInMillis,plate!!.capitalize(),contravention)
            Coroutines.io {
                saveBusquedas(listOf(busqueda))
                Log.e("viewmodel",busqueda.toString())
                buscarListener!!.onSucces(plate!!)
            }
        }
    }
}
