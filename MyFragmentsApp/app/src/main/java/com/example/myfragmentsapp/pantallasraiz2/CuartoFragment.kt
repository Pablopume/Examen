package com.example.myfragmentsapp.pantallasraiz2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.navigation.fragment.findNavController
import com.example.myfragmentsapp.Constantes
import com.example.myfragmentsapp.databinding.FragmentCuartoBinding

class CuartoFragment: Fragment() {
    private var _binding: FragmentCuartoBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCuartoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            irQuinto.setOnClickListener {
                val action = CuartoFragmentDirections.actionCuartoFragmentToQuintoFragment(
                    Constantes.DESDE_EL_CUARTO
                )
                findNavController().navigate(action)
            }
            irSexto.setOnClickListener{
                val action = CuartoFragmentDirections.actionCuartoFragmentoToSextoFragment(
                    Constantes.DESDE_EL_CUARTO
                )
                findNavController().navigate(action)
            }
        }
    }
}
