package com.juanfe.pyp.ui.bitacora

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.juanfe.pyp.data.AppDatabase
import com.juanfe.pyp.data.models.Busqueda
import com.juanfe.pyp.utlis.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BitacoraViewModel(var appDatabase: AppDatabase) : ViewModel() {
    val busquedas by lazyDeferred {
        getBusquedas()
    }

    suspend fun getBusquedas(): LiveData<MutableList<Busqueda>> {
        return withContext(Dispatchers.IO){
            appDatabase.getBusquedaDao().getBusquedas()
        }
    }
}
