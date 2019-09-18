package com.juanfe.pyp.ui.bitacora

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juanfe.pyp.data.AppDatabase

/**
 * viewmodel factory since a viewmodel cant have arguments in a constructor
 *
 */
@Suppress("UNCHECKED_CAST")
class BitacoraViewModelFactory(val database: AppDatabase): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BitacoraViewModel(database) as T
    }
}