package com.juanfe.pyp.ui.buscar

interface BuscarListener {
    fun onError(message: Int)
    fun onSucces(plate: String)
}