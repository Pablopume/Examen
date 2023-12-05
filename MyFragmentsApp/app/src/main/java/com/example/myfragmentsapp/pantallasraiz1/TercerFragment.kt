package com.example.myfragmentsapp.pantallasraiz1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myfragmentsapp.Constantes

import com.example.myfragmentsapp.databinding.FragmentTercerBinding

class TercerFragment : Fragment() {
    val args: TercerFragmentArgs by navArgs()
    private var _binding: FragmentTercerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentTercerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            texto2.text = args.mensaje
            irPrimero.setOnClickListener {
                val action = TercerFragmentDirections.actionTercerFragmentToPrimerFragment(
                    Constantes.DESDE_EL_TERCERO
                )
                findNavController().navigate(action)
            }
            irSegundo.setOnClickListener {
                val action = TercerFragmentDirections.actionTercerFragmentToSegundoFragment(
                    Constantes.DESDE_EL_TERCERO

                )
                findNavController().navigate(action)
            }

        }
    }
}