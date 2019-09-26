package com.juanfe.pyp.ui.buscar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.einvopos.e_invopos.utils.Coroutines
import com.juanfe.pyp.R
import com.juanfe.pyp.data.models.Busqueda
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.buscar_item.view.*
import java.time.format.DateTimeFormatter
import java.util.*
import java.text.SimpleDateFormat


/**
 * basic adapter to work with the recycler view
 */
class RecyclerViewBuscarAdapter(private val busquedas: List<Busqueda>) : RecyclerView.Adapter<RecyclerViewBuscarAdapter.ViewHolder>() {

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

    fun getContraventions(): Int {
        return busquedas.filter { busqueda -> busqueda.contravention }.size
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