package com.juanfe.pyp.ui.buscar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juanfe.pyp.data.AppDatabase
import com.juanfe.pyp.ui.bitacora.BitacoraViewModel

/**
 * viewmodel factory since a viewmodel cant have arguments in a constructor
 *
 */
@Suppress("UNCHECKED_CAST")
class BuscarViewModelFactory(val database: AppDatabase): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BuscarViewModel(database) as T
    }
}