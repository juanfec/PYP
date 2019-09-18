package com.juanfe.pyp.ui.bitacora

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.einvopos.e_invopos.utils.Coroutines

import com.juanfe.pyp.R
import com.juanfe.pyp.data.models.Busqueda
import com.juanfe.pyp.ui.buscar.RecyclerViewBuscarAdapter
import kotlinx.android.synthetic.main.bitacora_fragment.*
import kotlinx.android.synthetic.main.buscar_fragment.*
import okhttp3.internal.Internal.instance
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class Bitacora : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val factory: BitacoraViewModelFactory by instance()
    private lateinit var viewModel: BitacoraViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bitacora_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(BitacoraViewModel::class.java)
        bindUi()
    }

    fun bindUi() = Coroutines.main{
        val liveBusquedas = viewModel.busquedas.await()
        liveBusquedas.observe(this, Observer {
            initRecyclerView(it)
        })
    }

    fun initRecyclerView(listBusquedas: MutableList<Busqueda>){
        recyclervire_bitacora.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = RecyclerBitacoraAdapter(listBusquedas)
        }
    }
}
