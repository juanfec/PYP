package com.juanfe.pyp.ui.bitacora

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.einvopos.e_invopos.utils.Coroutines
import com.juanfe.pyp.R
import com.juanfe.pyp.data.AppDatabase
import com.juanfe.pyp.data.models.Busqueda
import com.juanfe.pyp.ui.buscar.BuscarListener
import com.juanfe.pyp.utlis.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BitacoraViewModel(var appDatabase: AppDatabase) : ViewModel() {
    val busquedas by lazyDeferred {
        getBusquedas()
    }
    var codigo: String? = null
    var description: String? = null
    var buscarListener: BuscarListener? = null

    suspend fun getBusquedas(): LiveData<MutableList<Busqueda>> {
        return withContext(Dispatchers.IO){
            appDatabase.getBusquedaDao().getBusquedas()
        }
    }

    fun editarLista(list: List<Busqueda>){
        appDatabase.getBusquedaDao().insertOrUpdate(list)
    }

    fun editar(view: View){
        //we clear our filtered search so we dont get duplicates
        if(codigo.isNullOrEmpty()){
            buscarListener!!.onError(R.string.campos_vacios)
        }else{
            editarLista(listOf(Busqueda(codigo!!,description!!)))
        }

    }

    fun borrar(view: View){
        //we clear our filtered search so we dont get duplicates

    }
}
