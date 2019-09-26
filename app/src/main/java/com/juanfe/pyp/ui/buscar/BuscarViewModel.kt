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
import java.util.*
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


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
            //we get the date and create an instance
            val date = Date()
            val calendarInstance = Calendar.getInstance()
            calendarInstance.time = date
            val hour = calendarInstance.get(Calendar.HOUR_OF_DAY) * 100 + calendarInstance.get(Calendar.MINUTE)
            //we get the intervals of hours
            val desde1 = Constants.PICOYPLACAHORA[0]
            val hasta1 = Constants.PICOYPLACAHORA[1]
            val desde2 = Constants.PICOYPLACAHORA[2]
            val hasta2 = Constants.PICOYPLACAHORA[3]
            //we decide if there is a contravention
            var last = plate!!.takeLast(1)
            var contravention = false
            Log.e("viewmodel",toString())
            if(Constants.PICOYPLACADIA[calendarInstance.time.day-1].contains(last.toInt())){
                if(
                    (hasta1 > desde1 && hour >= desde1 && hour <= hasta1 || hasta1 < desde1 && (hour >= desde1 || hour <= hasta1)) ||
                    (hasta2 > desde2 && hour >= desde2 && hour <= hasta2 || hasta1 < desde2 && (hour >= desde1 || hour <= hasta2)) ){
                    contravention = true
                }
            }
            var busqueda = Busqueda(calendarInstance.timeInMillis,plate!!.toUpperCase(),contravention)
            Coroutines.io {
                //we save the search also we update the view
                saveBusquedas(listOf(busqueda))
                //filteredSearch.add(busqueda)
                var list = getBusquedasByPlate(busqueda.plate)
                filteredSearch.addAll(list)
                Coroutines.main{
                    buscarListener!!.onSucces(plate!!)
                }
            }
        }
    }
}
