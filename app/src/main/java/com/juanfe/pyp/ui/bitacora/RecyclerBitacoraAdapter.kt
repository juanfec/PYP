package com.juanfe.pyp.ui.bitacora

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juanfe.pyp.R
import com.juanfe.pyp.data.models.Busqueda
import kotlinx.android.synthetic.main.buscar_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import com.juanfe.pyp.ui.bitacora.RecyclerBitacoraAdapter.OnTextClickListener





/**
 * basic adapter to work with the recycler view
 */
class RecyclerBitacoraAdapter(private val busquedas: List<Busqueda>, private val listener: OnTextClickListener) : RecyclerView.Adapter<RecyclerBitacoraAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.buscar_item, parent, false)
        return ViewHolder(view)
    }

    interface OnTextClickListener {
        fun onTextClick(plate: String, description: String)
    }

    override fun getItemCount(): Int {
        return busquedas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listener?.let { holder.bind(busquedas[position], it) }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
        fun bind(busqueda: Busqueda,listener:OnTextClickListener)= with(itemView) {
            placa_item.text = busqueda.plate
            fecha_item.text = busqueda.descripcion
            itemView.setOnClickListener {
                listener.onTextClick(busqueda.plate,busqueda.descripcion)
            }
        }

    }
}