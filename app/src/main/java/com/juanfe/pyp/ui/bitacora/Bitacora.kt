package com.juanfe.pyp.ui.bitacora

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.juanfe.pyp.R
import kotlinx.android.synthetic.main.buscar_fragment.*
import org.kodein.di.android.x.kodein
import org.kodein.di.KodeinAware
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class Bitacora : Fragment(), BitacoraListener, KodeinAware {
    override val kodein by kodein()

    private lateinit var viewModel: BitacoraViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bitacora_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BitacoraViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onError(message: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSucces() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
