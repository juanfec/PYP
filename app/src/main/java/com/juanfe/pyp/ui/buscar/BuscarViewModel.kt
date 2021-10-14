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
    var description: String? = null
    val regexPlate = Regex("[A-Za-z0-9]+")
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

    fun getBusquedaByDescription(description: String): MutableList<Busqueda> {
        return database.getBusquedaDao().getBusquedasByDescription(description)
    }

    /**
     * we look for a code or a description
     */
    fun buscar(view: View){
        //we clear our filtered search so we dont get duplicates
        filteredSearch.clear()
        //some validation
        if(plate.isNullOrEmpty()&&description.isNullOrEmpty()){
            buscarListener!!.onError(R.string.campos_vacios)
        }else{
            Coroutines.io {
                var list = listOf<Busqueda>()
                if (!description.isNullOrEmpty()) {
                    if(description!!.matches(regexPlate)){
                        list = getBusquedaByDescription(description!!)
                    }else {
                        buscarListener!!.onError(R.string.campos_incorrectos)
                    }

                }
                if (!plate.isNullOrEmpty()) {
                    if(plate!!.matches(regexPlate)){
                        list = getBusquedaByDescription(plate!!)
                    }else {
                        buscarListener!!.onError(R.string.campos_incorrectos)
                    }

                }
                //filteredSearch.add(busqueda)
                filteredSearch.addAll(list)
                Coroutines.main{
                    buscarListener!!.onSucces(plate!!)
                }
            }
        }
    }

    /**
     * we look for a code or a description
     */
    fun agregar(view: View){
        //we clear our filtered search so we dont get duplicates

        //some validation
        if (plate.isNullOrEmpty()&& description.isNullOrEmpty()){
            buscarListener!!.onError(R.string.campos_vacios)
        }else {
            if(!plate!!.matches(regexPlate)&&!description!!.matches(regexPlate)){
                buscarListener!!.onError(R.string.campos_incorrectos)
            }else{

                var busqueda = Busqueda(plate!!.toUpperCase(), description!!)
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
}
