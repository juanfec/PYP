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

/**
 * this viewmodel handles all the business logic
 */
class BuscarViewModel(var database: AppDatabase) : ViewModel() {
    var buscarListener: BuscarListener? = null
    var plate: String? = null
    val regexPlate = Regex("[a-zA-Z]{3}[0-9]{3}")
    val busquedas by lazyDeferred {
        getBusquedas()
    }
    var filteredSearch = mutableListOf<Busqueda>()

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
        //we clear our filtered search so we dont get duplicates
        filteredSearch.clear()
        //some validation
        if (plate.isNullOrEmpty() ){
            buscarListener!!.onError(R.string.campos_vacios)
        }else if(!plate!!.matches(regexPlate)){
            buscarListener!!.onError(R.string.campos_incorrectos)
        }else{
            //we decide if there is a contravention
            var last = plate!!.takeLast(1)
            var contravention = false
            if(Constants.PICOYPLACA[Calendar.getInstance().time.day-1].contains(last.toInt())){
                contravention = true
            }
            var busqueda = Busqueda(Calendar.getInstance().timeInMillis,plate!!.toUpperCase(),contravention)
            Coroutines.io {
                //we save the search also we update the view
                saveBusquedas(listOf(busqueda))
                filteredSearch.add(busqueda)
                var list = getBusquedasByPlate(busqueda.plate)
                filteredSearch.addAll(list)
                Coroutines.main{
                    buscarListener!!.onSucces(plate!!)
                }
            }
        }
    }
}
