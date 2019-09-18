package com.juanfe.pyp.ui.bitacora

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juanfe.pyp.R
import com.juanfe.pyp.data.models.Busqueda
import kotlinx.android.synthetic.main.buscar_item.view.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * basic adapter to work with the recycler view
 */
class RecyclerBitacoraAdapter(private val busquedas: List<Busqueda>) : RecyclerView.Adapter<RecyclerBitacoraAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.buscar_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return busquedas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(busquedas[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(busqueda: Busqueda)= with(itemView) {
            placa_item.text = busqueda.plate
            var date = Date(busqueda.date)
            val pattern = "dd-MM-yyyy"
            val simpleDateFormat = SimpleDateFormat(pattern)
            fecha_item.text = simpleDateFormat.format(date)
            if(busqueda.contravention){
                contravencion_item.text = resources.getString(R.string.si)
                contravencion_item.setTextColor(resources.getColor(R.color.colorAccent))
            }else{
                contravencion_item.text = resources.getString(R.string.no)
                contravencion_item.setTextColor(resources.getColor(R.color.colorPrimary))
            }
        }
    }

}