package com.juanfe.pyp.ui.buscar

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.einvopos.e_invopos.utils.Coroutines

import com.juanfe.pyp.R
import com.juanfe.pyp.data.models.Busqueda
import com.juanfe.pyp.databinding.BuscarFragmentBinding
import com.juanfe.pyp.utlis.snackbar
import kotlinx.android.synthetic.main.buscar_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class Buscar : Fragment(), KodeinAware, BuscarListener {

    override val kodein by kodein()
    private lateinit var viewModel: BuscarViewModel
    private val factory : BuscarViewModelFactory by instance()
    lateinit var binding: BuscarFragmentBinding
    private lateinit var mAdapter: RecyclerViewBuscarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.buscar_fragment, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(BuscarViewModel::class.java)
        viewModel.buscarListener=this
        binding.viewmodel = viewModel
        initRecyclerView(viewModel.filteredSearch)

    }

    override fun onError(message: Int) {
        view!!.snackbar(getString(message))
    }

    override fun onSucces(plate : String) {
        mAdapter.notifyDataSetChanged()

    }

/*    *//**
     * we bind the ui with the data
     *//*
    fun bindUi() = Coroutines.main{
        val liveBusquedas = viewModel.busquedas.await()
        liveBusquedas.observe(this, Observer {
            initRecyclerView(it)
        })
    }*/

    /**
     * we initialized the recyclerview
     */
    fun initRecyclerView(listItems: MutableList<Busqueda>){
        mAdapter = RecyclerViewBuscarAdapter(listItems)
        buscar_recycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
    }

}
