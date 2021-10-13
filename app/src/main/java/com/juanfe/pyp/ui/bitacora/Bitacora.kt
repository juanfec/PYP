package com.juanfe.pyp.ui.bitacora

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.einvopos.e_invopos.utils.Coroutines

import com.juanfe.pyp.R
import com.juanfe.pyp.data.models.Busqueda
import com.juanfe.pyp.databinding.BitacoraFragmentBinding
import com.juanfe.pyp.databinding.BuscarFragmentBinding
import com.juanfe.pyp.ui.buscar.BuscarListener
import com.juanfe.pyp.ui.buscar.RecyclerViewBuscarAdapter
import com.juanfe.pyp.utlis.snackbar
import kotlinx.android.synthetic.main.bitacora_fragment.*
import kotlinx.android.synthetic.main.buscar_fragment.*
import okhttp3.internal.Internal.instance
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class Bitacora : Fragment(), KodeinAware , RecyclerBitacoraAdapter.OnTextClickListener,
    BuscarListener {
    override val kodein by kodein()
    private val factory: BitacoraViewModelFactory by instance()
    private lateinit var viewModel: BitacoraViewModel
    lateinit var binding: BitacoraFragmentBinding
    private lateinit var mAdapter: RecyclerBitacoraAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.bitacora_fragment, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(BitacoraViewModel::class.java)
        bindUi()
        viewModel.buscarListener = this
        binding.viewmodel = viewModel
    }

    fun bindUi() = Coroutines.main{
        val liveBusquedas = viewModel.busquedas.await()
        liveBusquedas.observe(viewLifecycleOwner, Observer {
            initRecyclerView(it)
        })
    }

    fun initRecyclerView(listBusquedas: MutableList<Busqueda>){
        mAdapter = RecyclerBitacoraAdapter(listBusquedas, this)
        recyclervire_bitacora.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
    }

    override fun onTextClick(plate: String, description: String) {
        codigoEditarBorrar.text = plate
        DescriptionEditarBorrar.setText(description)
    }

    override fun onError(message: Int) {
        view!!.snackbar(getString(message))
    }

    override fun onSucces(plate : String) {
        mAdapter.notifyDataSetChanged()
    }

}
