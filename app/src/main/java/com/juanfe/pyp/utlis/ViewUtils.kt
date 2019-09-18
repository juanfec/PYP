package com.juanfe.pyp.utlis

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.*

/**
 * this extension functions are a easy way to make toast and snackbars
 */

fun Context.toast(message:String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun View.snackbar(message: String){
    make(this, message , LENGTH_LONG).also { snackbar ->
        snackbar.setAction("OK"){
               snackbar.dismiss()
        }
    }.show()
}